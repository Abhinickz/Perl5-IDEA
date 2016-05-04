/*
 * Copyright 2016 Alexandr Evstigneev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.perl5.lang.perl.idea.run.debugger;

import com.intellij.openapi.vfs.CharsetToolkit;
import com.intellij.util.concurrency.Semaphore;
import com.intellij.util.containers.ByteArrayList;
import com.intellij.xdebugger.XDebugSession;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by hurricup on 04.05.2016.
 */
public class PerlDebugThread extends Thread
{
	private XDebugSession mySession;
	private Socket mySocket;
	private OutputStream myOutputStream;
	private InputStream myInputStream;

	private Semaphore myResponseSemaphore = new Semaphore();
	private boolean myWaitForResponse = false;
	private byte[] myResponseBuffer;

	private boolean myStop = false;

	public PerlDebugThread(XDebugSession session)
	{
		super("PerlDebugThread");
		mySession = session;
	}

	@Override
	public void run()
	{
		try
		{
			mySocket = new Socket("localhost", 12345);
			myOutputStream = mySocket.getOutputStream();
			myInputStream = mySocket.getInputStream();
			ByteArrayList response = new ByteArrayList();

			while (!myStop)
			{
				response.clear();

				// reading bytes
				while (true)
				{
					byte newByte = (byte) myInputStream.read();
					if (newByte == '\n')
					{
						break;
					}
					else
					{
						response.add(newByte);
					}
				}

				if (myWaitForResponse)
				{
					myResponseBuffer = response.toNativeArray();
					myWaitForResponse = false;
					myResponseSemaphore.up();
				}
				else
				{
					processResponse(response);
				}
			}

		} catch (IOException e)
		{
			if (!myStop)
			{
				e.printStackTrace();
			}
		}
	}

	private void processResponse(ByteArrayList responseBytes)
	{
		String response = new String(responseBytes.toNativeArray(), CharsetToolkit.UTF8_CHARSET);
		if (response.equals("STOPPED"))
		{
			mySession.positionReached(new PerlSuspendContext());
		}
		else
		{
			System.err.println("Unhandled process data: " + response);
		}
	}

	public void sendString(String string)
	{
		if (mySocket == null)
			return;

		try
		{
			myOutputStream.write(string.getBytes());
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Nullable
	public synchronized String sendStringAndGetResponse(String string)
	{
		if (mySocket == null)
			return null;

		myResponseSemaphore.down();
		myWaitForResponse = true;

		String response = null;
		try
		{
			myOutputStream.write(string.getBytes());
			myResponseSemaphore.waitFor();
			response = new String(myResponseBuffer, CharsetToolkit.UTF8_CHARSET);

		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return response;
	}

	public Socket getSocket()
	{
		return mySocket;
	}

	public void setStop()
	{
		myStop = true;
		try
		{
			if (myInputStream != null)
				myInputStream.close();
		} catch (IOException e)
		{
		}
		try
		{
			if (myOutputStream != null)
				myOutputStream.close();
		} catch (IOException e)
		{
		}
		try
		{
			if (mySocket != null)
				mySocket.close();
		} catch (IOException e)
		{
		}

	}
}
