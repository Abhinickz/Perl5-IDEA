/*
 * Copyright 2015 Alexandr Evstigneev
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

package com.perl5.lang.mason2.lexer;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import com.perl5.lang.htmlmason.lexer.AbstractMasonLexer;
import com.perl5.lang.mason2.elementType.Mason2ElementTypes;
import com.perl5.lang.perl.lexer.PerlLexer;
import gnu.trove.THashMap;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hurricup on 20.12.2015.
 */
@SuppressWarnings("Duplicates")
public class MasonTemplatingLexer extends AbstractMasonLexer implements Mason2ElementTypes
{
	public static final Pattern MASON_EXPRESSION_FILTER_BLOCK = Pattern.compile(
			"\\|\\s*"
					+ "(?:" + IDENTIFIER_PATTERN + "\\s*,\\s*" + ")*"
					+ IDENTIFIER_PATTERN
					+ "\\s*" + KEYWORD_BLOCK_CLOSER
	);

	public static final Pattern MASON_SIMPLE_OPENERS = Pattern.compile(
			"<%(" +
					KEYWORD_CLASS + "|" +
					KEYWORD_DOC + "|" +
					KEYWORD_FLAGS + "|" +
					KEYWORD_INIT + "|" +
					KEYWORD_PERL + "|" +
					KEYWORD_TEXT +
					")>"
	);

	public static final Pattern MASON_OPENERS = Pattern.compile(
			"<%(" +
					KEYWORD_METHOD + "|" +
					KEYWORD_FILTER + "|" +
					KEYWORD_AFTER + "|" +
					KEYWORD_AUGMENT + "|" +
					KEYWORD_AROUND + "|" +
					KEYWORD_BEFORE + "|" +
					KEYWORD_OVERRIDE +
					")"
	);

	public static final Pattern MASON_CLOSERS = Pattern.compile(
			"</%(" +
					KEYWORD_METHOD + "|" +
					KEYWORD_FILTER + "|" +
					KEYWORD_AFTER + "|" +
					KEYWORD_AUGMENT + "|" +
					KEYWORD_AROUND + "|" +
					KEYWORD_BEFORE + "|" +
					KEYWORD_OVERRIDE +
					")>"
	);

	public static final Pattern MASON_SELF_POINTER_PATTERN = Pattern.compile(
			"\\$\\." + PerlLexer.IDENTIFIER_PATTERN
	);

	public static final Pattern MASON_FILTERED_BLOCK_OPENER_PATTERN = Pattern.compile(
			"\\{\\{\\s*(?:#.*|\\}\\})?\n"
	);

	public static final Pattern MASON_FILTERED_BLOCK_CLOSER_PATTERN = Pattern.compile(
			"\\}\\}\\s*(?:#.*)?\n"
	);


	// lexical states
	public static final int LEX_MASON_HTML_BLOCK = LEX_CUSTOM1;             // template block
	public static final int LEX_MASON_PERL_BLOCK = LEX_CUSTOM2;             // complicated blocks <%kw>...</%kw>
	public static final int LEX_MASON_PERL_LINE = LEX_CUSTOM3;              // % ...
	public static final int LEX_MASON_PERL_EXPR_BLOCK = LEX_CUSTOM4;        // <% ... | id1, id2, ... %>
	public static final int LEX_MASON_PERL_EXPR_FILTER_BLOCK = LEX_CUSTOM5; // | id1, id2, ... %> // same as above, but after pipe
	public static final int LEX_MASON_PERL_CALL_BLOCK = LEX_CUSTOM6;        // <& ... &>
	public static final int LEX_MASON_OPENING_TAG = LEX_CUSTOM7;            // lexing tag additional info

	private static final Map<String, IElementType> OPEN_TOKENS_MAP = new THashMap<String, IElementType>();
	private static final Map<String, String> OPEN_CLOSE_MAP = new THashMap<String, String>();
	private static final Map<String, IElementType> CLOSE_TOKENS_MAP = new THashMap<String, IElementType>();

	static
	{
		OPEN_TOKENS_MAP.put(KEYWORD_CLASS_OPENER, MASON_CLASS_OPENER);
		OPEN_TOKENS_MAP.put(KEYWORD_DOC_OPENER, MASON_DOC_OPENER);
		OPEN_TOKENS_MAP.put(KEYWORD_FLAGS_OPENER, MASON_FLAGS_OPENER);
		OPEN_TOKENS_MAP.put(KEYWORD_INIT_OPENER, MASON_INIT_OPENER);
		OPEN_TOKENS_MAP.put(KEYWORD_PERL_OPENER, MASON_PERL_OPENER);
		OPEN_TOKENS_MAP.put(KEYWORD_TEXT_OPENER, MASON_TEXT_OPENER);

		// parametrized
		OPEN_TOKENS_MAP.put(KEYWORD_METHOD_OPENER, MASON_METHOD_OPENER);
		OPEN_TOKENS_MAP.put(KEYWORD_FILTER_OPENER, MASON_FILTER_OPENER);
		OPEN_TOKENS_MAP.put(KEYWORD_AFTER_OPENER, MASON_AFTER_OPENER);
		OPEN_TOKENS_MAP.put(KEYWORD_AUGMENT_OPENER, MASON_AUGMENT_OPENER);
		OPEN_TOKENS_MAP.put(KEYWORD_AROUND_OPENER, MASON_AROUND_OPENER);
		OPEN_TOKENS_MAP.put(KEYWORD_BEFORE_OPENER, MASON_BEFORE_OPENER);
		OPEN_TOKENS_MAP.put(KEYWORD_OVERRIDE_OPENER, MASON_OVERRIDE_OPENER);
	}

	static
	{
		OPEN_CLOSE_MAP.put(KEYWORD_CLASS_OPENER, KEYWORD_CLASS_CLOSER);
		OPEN_CLOSE_MAP.put(KEYWORD_DOC_OPENER, KEYWORD_DOC_CLOSER);
		OPEN_CLOSE_MAP.put(KEYWORD_FLAGS_OPENER, KEYWORD_FLAGS_CLOSER);
		OPEN_CLOSE_MAP.put(KEYWORD_INIT_OPENER, KEYWORD_INIT_CLOSER);
		OPEN_CLOSE_MAP.put(KEYWORD_PERL_OPENER, KEYWORD_PERL_CLOSER);
		OPEN_CLOSE_MAP.put(KEYWORD_TEXT_OPENER, KEYWORD_TEXT_CLOSER);

		// parametrized
		OPEN_CLOSE_MAP.put(KEYWORD_METHOD_OPENER, KEYWORD_METHOD_CLOSER);
		OPEN_CLOSE_MAP.put(KEYWORD_FILTER_OPENER, KEYWORD_FILTER_CLOSER);
		OPEN_CLOSE_MAP.put(KEYWORD_AFTER_OPENER, KEYWORD_AFTER_CLOSER);
		OPEN_CLOSE_MAP.put(KEYWORD_AUGMENT_OPENER, KEYWORD_AUGMENT_CLOSER);
		OPEN_CLOSE_MAP.put(KEYWORD_AROUND_OPENER, KEYWORD_AROUND_CLOSER);
		OPEN_CLOSE_MAP.put(KEYWORD_BEFORE_OPENER, KEYWORD_BEFORE_CLOSER);
		OPEN_CLOSE_MAP.put(KEYWORD_OVERRIDE_OPENER, KEYWORD_OVERRIDE_CLOSER);
	}

	static
	{
		CLOSE_TOKENS_MAP.put(KEYWORD_CLASS_CLOSER, MASON_CLASS_CLOSER);
		CLOSE_TOKENS_MAP.put(KEYWORD_DOC_CLOSER, MASON_DOC_CLOSER);
		CLOSE_TOKENS_MAP.put(KEYWORD_FLAGS_CLOSER, MASON_FLAGS_CLOSER);
		CLOSE_TOKENS_MAP.put(KEYWORD_INIT_CLOSER, MASON_INIT_CLOSER);
		CLOSE_TOKENS_MAP.put(KEYWORD_PERL_CLOSER, MASON_PERL_CLOSER);
		CLOSE_TOKENS_MAP.put(KEYWORD_TEXT_CLOSER, MASON_TEXT_CLOSER);

		// parametrized
		CLOSE_TOKENS_MAP.put(KEYWORD_METHOD_CLOSER, MASON_METHOD_CLOSER);
		CLOSE_TOKENS_MAP.put(KEYWORD_FILTER_CLOSER, MASON_FILTER_CLOSER);
		CLOSE_TOKENS_MAP.put(KEYWORD_AFTER_CLOSER, MASON_AFTER_CLOSER);
		CLOSE_TOKENS_MAP.put(KEYWORD_AUGMENT_CLOSER, MASON_AUGMENT_CLOSER);
		CLOSE_TOKENS_MAP.put(KEYWORD_AROUND_CLOSER, MASON_AROUND_CLOSER);
		CLOSE_TOKENS_MAP.put(KEYWORD_BEFORE_CLOSER, MASON_BEFORE_CLOSER);
		CLOSE_TOKENS_MAP.put(KEYWORD_OVERRIDE_CLOSER, MASON_OVERRIDE_CLOSER);
	}

	private String BLOCK_CLOSE_TAG;


	public MasonTemplatingLexer(Project project)
	{
		super(project);
	}

	@Override
	public int getInitialCustomState()
	{
		return LEX_MASON_HTML_BLOCK;
	}


	public IElementType perlAdvance() throws IOException
	{
		CharSequence buffer = getBuffer();
		int tokenStart = getNextTokenStart();
		int bufferEnd = getBufferEnd();
		int currentCustomState = getCustomState();

		if (bufferEnd == 0 || tokenStart >= bufferEnd)
		{
			return super.perlAdvance();
		}

		char currentChar = buffer.charAt(tokenStart);

		if ((currentCustomState == LEX_MASON_PERL_LINE || currentCustomState == LEX_MASON_PERL_BLOCK) &&
				currentChar == '$' &&
				tokenStart + 2 < bufferEnd &&
				buffer.charAt(tokenStart + 1) == '.' &&
				MASON_SELF_POINTER_PATTERN.matcher(buffer).region(tokenStart, bufferEnd).lookingAt()
				)
		{
			setTokenStart(tokenStart);
			setTokenEnd(tokenStart + KEYWORD_SELF_POINTER.length());
			return MASON_SELF_POINTER;
		}


		if (currentCustomState == LEX_MASON_PERL_LINE)
		{
			if (currentChar == '\n')
			{
				setTokenStart(tokenStart);
				setTokenEnd(tokenStart + 1);

				setCustomState(getInitialCustomState());
				return SEMICOLON;
			}
			else if (tokenStart + 1 < bufferEnd)
			{
				char nextChar = buffer.charAt(tokenStart + 1);
				if (currentChar == '{' && nextChar == '{' &&
						MASON_FILTERED_BLOCK_OPENER_PATTERN.matcher(buffer).region(tokenStart, bufferEnd).lookingAt()
						)
				{
					setTokenStart(tokenStart);
					setTokenEnd(tokenStart + KEYWORD_FILTERED_BLOCK_OPENER.length());
					return MASON_FILTERED_BLOCK_OPENER;
				}
				else if (currentChar == '}' && nextChar == '}' &&
						MASON_FILTERED_BLOCK_CLOSER_PATTERN.matcher(buffer).region(tokenStart, bufferEnd).lookingAt()
						)
				{
					setTokenStart(tokenStart);
					setTokenEnd(tokenStart + KEYWORD_FILTERED_BLOCK_CLOSER.length());
					return MASON_FILTERED_BLOCK_CLOSER;
				}
			}
		}
		else if (currentCustomState == LEX_MASON_OPENING_TAG && currentChar == '>')
		{
			setTokenStart(tokenStart);
			setTokenEnd(tokenStart + 1);
			setCustomState(getInitialCustomState());
			return MASON_TAG_CLOSER;
		}
		else if (currentCustomState == LEX_MASON_PERL_EXPR_BLOCK && currentChar == '|')
		{
			Matcher m = MASON_EXPRESSION_FILTER_BLOCK.matcher(buffer);
			m.region(tokenStart, bufferEnd);
			if (m.lookingAt())
			{
				setTokenStart(tokenStart);
				setTokenEnd(tokenStart + 1);
				setCustomState(LEX_MASON_PERL_EXPR_FILTER_BLOCK);
				return MASON_EXPR_FILTER_PIPE;
			}
		}
		else if (currentCustomState == LEX_MASON_PERL_EXPR_BLOCK || currentCustomState == LEX_MASON_PERL_EXPR_FILTER_BLOCK)
		{
			if (isBufferAtString(buffer, tokenStart, KEYWORD_BLOCK_CLOSER))
			{
				setTokenStart(tokenStart);
				setTokenEnd(tokenStart + KEYWORD_BLOCK_CLOSER.length());

				setCustomState(getInitialCustomState());
				return MASON_BLOCK_CLOSER;
			}

			if (parseTailSpaces(buffer, tokenStart, bufferEnd, KEYWORD_BLOCK_CLOSER))
			{
				return TokenType.WHITE_SPACE;
			}
		}
		else if (currentCustomState == LEX_MASON_PERL_CALL_BLOCK)
		{
			if (isBufferAtString(buffer, tokenStart, KEYWORD_CALL_CLOSER))
			{
				setTokenStart(tokenStart);
				setTokenEnd(tokenStart + KEYWORD_CALL_CLOSER.length());

				setCustomState(getInitialCustomState());
				return MASON_CALL_CLOSER;
			}

			if (parseTailSpaces(buffer, tokenStart, bufferEnd, KEYWORD_CALL_CLOSER))
			{
				return TokenType.WHITE_SPACE;
			}

		}
		else if (currentCustomState == LEX_MASON_PERL_BLOCK && isBufferAtString(buffer, tokenStart, BLOCK_CLOSE_TAG))
		{
			setTokenStart(tokenStart);
			setTokenEnd(tokenStart + BLOCK_CLOSE_TAG.length());
			setCustomState(getInitialCustomState());
			return CLOSE_TOKENS_MAP.get(BLOCK_CLOSE_TAG);
		}
		else if (currentCustomState == getInitialCustomState())
		{
			int offset = tokenStart;

			boolean blockStart = false;
			Matcher m;
			Matcher matcherSimpleOpener = null;
			Matcher matcherOpener = null;
			int lastNonspaceCharacterOffset = -1;

			for (; offset < bufferEnd; offset++)
			{
				currentChar = buffer.charAt(offset);

				if (offset + 1 < bufferEnd &&
						currentChar == '<' &&
						buffer.charAt(offset + 1) == '%' &&
						(
								offset + 2 < bufferEnd && Character.isWhitespace(buffer.charAt(offset + 2)) ||    // <%
										(matcherSimpleOpener = MASON_SIMPLE_OPENERS.matcher(buffer).region(offset, bufferEnd)).lookingAt() ||    // <%text>
										(matcherOpener = MASON_OPENERS.matcher(buffer).region(offset, bufferEnd)).lookingAt()    // <%augment...
						)
						)
				{
					blockStart = true;
					break;
				}
				else if (offset < bufferEnd - 2 &&
						currentChar == '<' &&
						buffer.charAt(offset + 1) == '/' &&
						buffer.charAt(offset + 2) == '%' &&
						(m = MASON_CLOSERS.matcher(buffer).region(offset, bufferEnd)).lookingAt()
						)
				{
					String tag = m.group(0);
					pushPreparsedToken(offset, offset + tag.length(), CLOSE_TOKENS_MAP.get(tag));
					break;
				}
				else if (offset < bufferEnd - 2 && currentChar == '<' && buffer.charAt(offset + 1) == '&' && Character.isWhitespace(buffer.charAt(offset + 2)))
				{
					pushPreparsedToken(offset, offset + KEYWORD_CALL_OPENER.length(), MASON_CALL_OPENER);
					parseCallComponentPath(offset + KEYWORD_CALL_OPENER.length());
					setCustomState(LEX_MASON_PERL_CALL_BLOCK);
					break;
				}
				else if (currentChar == '%' && (offset == 0 || buffer.charAt(offset - 1) == '\n'))
				{
					pushPreparsedToken(offset, offset + 1, MASON_LINE_OPENER);
					setCustomState(LEX_MASON_PERL_LINE);
					break;
				}
				else if (!Character.isWhitespace(currentChar))
				{
					lastNonspaceCharacterOffset = offset;
				}
			}

			reLexHTMLBLock(tokenStart, offset, lastNonspaceCharacterOffset, MASON_TEMPLATE_BLOCK_HTML);

			if (offset >= bufferEnd)  // end of file, html block
			{
				yybegin(YYINITIAL);
			}
			else if (blockStart)
			{
				if (matcherSimpleOpener != null && matcherSimpleOpener.lookingAt())
				{
					// check for unnamed block
					String openingTag = matcherSimpleOpener.group(0);
					pushPreparsedToken(offset, offset + openingTag.length(), OPEN_TOKENS_MAP.get(openingTag));
					BLOCK_CLOSE_TAG = OPEN_CLOSE_MAP.get(openingTag);

					if (openingTag.equals(KEYWORD_DOC_OPENER))
					{
						offset += openingTag.length();
						int commentStart = offset;
						boolean gotCloseTag = false;

						while (offset < bufferEnd)
						{
							if (offset <= bufferEnd - KEYWORD_DOC_CLOSER.length() &&
									buffer.charAt(offset) == '<' &&
									buffer.charAt(offset + 1) == '/' &&
									buffer.charAt(offset + 2) == '%' &&
									buffer.charAt(offset + 3) == 'd' &&
									buffer.charAt(offset + 4) == 'o' &&
									buffer.charAt(offset + 5) == 'c' &&
									buffer.charAt(offset + 6) == '>'
									)
							{
								gotCloseTag = true;
								break;
							}
							offset++;
						}
						if (offset > commentStart)
						{
							pushPreparsedToken(commentStart, offset, COMMENT_BLOCK);
						}
						if (gotCloseTag)
						{
							pushPreparsedToken(offset, offset + KEYWORD_DOC_CLOSER.length(), MASON_DOC_CLOSER);
						}
					}
					else if (openingTag.equals(KEYWORD_TEXT_OPENER))
					{
						offset += openingTag.length();
						int commentStart = offset;
						boolean gotCloseTag = false;

						while (offset < bufferEnd)
						{
							if (offset <= bufferEnd - KEYWORD_TEXT_CLOSER.length() &&
									buffer.charAt(offset) == '<' &&
									buffer.charAt(offset + 1) == '/' &&
									buffer.charAt(offset + 2) == '%' &&
									buffer.charAt(offset + 3) == 't' &&
									buffer.charAt(offset + 4) == 'e' &&
									buffer.charAt(offset + 5) == 'x' &&
									buffer.charAt(offset + 6) == 't' &&
									buffer.charAt(offset + 7) == '>'
									)
							{
								gotCloseTag = true;
								break;
							}
							offset++;
						}

						if (offset > commentStart)
						{
							pushPreparsedToken(commentStart, offset, STRING_CONTENT);
						}
						if (gotCloseTag)
						{
							pushPreparsedToken(offset, offset + KEYWORD_TEXT_CLOSER.length(), MASON_TEXT_CLOSER);
						}
					}
					else
					{
						setCustomState(LEX_MASON_PERL_BLOCK);// fixme we should capture text here
					}
				}
				else if (matcherOpener != null && matcherOpener.lookingAt())
				{
					// check for named block
					String openingTag = matcherOpener.group(0);
					pushPreparsedToken(offset, offset + openingTag.length(), OPEN_TOKENS_MAP.get(openingTag));
					setCustomState(LEX_MASON_OPENING_TAG);
				}
				else
				{
					assert Character.isWhitespace(buffer.charAt(offset + 2));
					pushPreparsedToken(offset, offset + KEYWORD_BLOCK_OPENER.length(), MASON_BLOCK_OPENER);
					setCustomState(LEX_MASON_PERL_EXPR_BLOCK);
				}
			}

			if (!preparsedTokensList.isEmpty())
			{
				return getPreParsedToken();
			}
		}
		return super.perlAdvance();
	}

/*
	@Override
	public void adjustCommentToken()
	{
		int customState = getCustomState();
		if (customState == LEX_MASON_PERL_EXPR_BLOCK || customState == LEX_MASON_PERL_EXPR_FILTER_BLOCK)
		{
			int endIndex = StringUtil.indexOf(yytext(), KEYWORD_BLOCK_CLOSER);
			if (endIndex > -1)
			{
				setTokenEnd(getTokenStart() + endIndex);
			}
		}
	}
*/

	@Override
	protected String getKeywordCallCloser()
	{
		return KEYWORD_CALL_CLOSER;
	}

}
