/*
    Copyright 2015 Alexandr Evstigneev

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/
package com.perl5.lang.perl.lexer;

import com.perl5.lang.perl.util.PerlSubUtil;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;


%%

%class PerlLexerGenerated
%extends PerlBaseLexer
%abstract
%unicode
%public


%function perlAdvance
%type IElementType


%{

    protected int trenarCounter = 0;

	public abstract IElementType startRegexp();
	public abstract IElementType getIdentifierToken();
	public abstract IElementType parseHeredocOpener();
	public abstract IElementType parseHeredocOpenerBackref();
%}


/*
// Char classes
*/
NEW_LINE = \R
WHITE_SPACE     = [ \t\f]

// http://perldoc.perl.org/perldata.html#Identifier-parsing
PERL_XIDS = [\w && \p{XID_Start}\d_] // seems in java \d does not matches XID_Start
PERL_XIDC = [\w && \p{XID_Continue}]

IDENTIFIER = {PERL_XIDS} {PERL_XIDC}*

//BAREWORD_MINUS = "-" * {IDENTIFIER}

QUALIFIED_IDENTIFIER = ("::"* "'" ?) ? {IDENTIFIER} (("::"+ "'" ? | "::"* "'" ) {IDENTIFIER} )*  "::" *
PACKAGE_SHORT = "::"+ "'" ?

//DQ_STRING = "\"" ([^\"]|"\\\\"|"\\\"" )* "\""	// for lpe
//SQ_STRING = "\'" ([^\']|"\\\\"|"\\\'" )* "\'"
//XQ_STRING = "\`" ([^\`]|"\\\\"|"\\\`" )* "\`"

QUOTE_LIKE_SUFFIX= "'"?
CORE_PREFIX = "CORE::"?

PERL_VERSION_CHUNK = [0-9][0-9_]*
PERL_VERSION = "v"?{PERL_VERSION_CHUNK}("." {PERL_VERSION_CHUNK})*
// heading _ removed to avoid @_ parsing as sigil-number

// duplicated in Pod lexer
NUMBER_EXP = [eE][+-]?[0-9_]+
NUMBER_INT = [0-9][0-9_]*
NUMBER_HEX = "0"[xX][0-9a-fA-F_]+
NUMBER_BIN = "0"[bB][01_]+

SPECIAL_VARIABLE_NAME = [\"\'\[\]\`\\!\%&\(\)\+,-./\;<=>|~?:*\^@_]
CAPPED_SINGLE_LETTER_VARIABLE_NAME = "^"[\]\[A-Z\^_?\\]
VARIABLE_NAME = {QUALIFIED_IDENTIFIER} | {CAPPED_SINGLE_LETTER_VARIABLE_NAME} | {SPECIAL_VARIABLE_NAME}

CAPPED_BRACED_VARIABLE = {CAPPED_SINGLE_LETTER_VARIABLE_NAME}[\w_]*
BRACED_VARIABLE_NAME = "{" ({VARIABLE_NAME}|"$"|{CAPPED_BRACED_VARIABLE}) "}"

// atm making the same, but seems unary are different
PERL_OPERATORS_FILETEST = "-" [rwxoRWXOezsfdlpSbctugkTBMAC][^a-zA-Z0-9_]

HEREDOC_MARKER = [a-zA-Z0-9_]+
HEREDOC_MARKER_DQ = [^\"\n\r]*
HEREDOC_MARKER_SQ = [^\'\n\r]*
HEREDOC_MARKER_XQ = [^\`\n\r]*
HEREDOC_OPENER = "<<"({WHITE_SPACE}* \'{HEREDOC_MARKER_SQ}\' | {WHITE_SPACE}* \"{HEREDOC_MARKER_DQ}\" | {WHITE_SPACE}* \`{HEREDOC_MARKER_XQ}\` | {HEREDOC_MARKER})
HEREDOC_OPENER_BACKREF = "<<\\"[a-zA-Z0-9_]+

END_BLOCK = "__END__" [^]+
DATA_BLOCK = "__DATA__" [^] +

POD_START = "="[\w][.]*
POD_LINE = ([.]+ {NEW_LINE} ? | {NEW_LINE})
POD_END = "=cut" (\s [.]*) {NEW_LINE}

// original list taken from http://www.perlmonks.org/?node_id=1131277
NAMED_UNARY_OPERATORS = "write"|"values"|"umask"|"ucfirst"|"uc"|"telldir"|"tell"|"study"|"stat"|"srand"|"sqrt"|"sleep"|"sin"|"shift"|"setservent"|"setprotoent"|"setnetent"|"sethostent"|"scalar"|"rmdir"|"rewinddir"|"reset"|"ref"|"readpipe"|"readlink"|"readline"|"readdir"|"rand"|"quotemeta"|"prototype"|"pop"|"ord"|"oct"|"lstat"|"log"|"localtime"|"length"|"lcfirst"|"lc"|"keys"|"int"|"hex"|"gmtime"|"getsockname"|"getpwuid"|"getpwnam"|"getprotobyname"|"getpgrp"|"getpeername"|"getnetbyname"|"gethostbyname"|"getgrnam"|"getgrgid"|"getc"|"fileno"|"fc"|"exp"|"exit"|"exists"|"evalbytes"|"eof"|"each"|"defined"|"cos"|"closedir"|"close"|"chroot"|"chr"|"chdir"|"caller"|"alarm"|"abs"

BLOCK_NAMES = "BEGIN"|"UNITCHECK"|"CHECK"|"INIT"|"END"|"AUTOLOAD"|"DESTROY"
TAG_NAMES = "__FILE__"|"__LINE__"|"__PACKAGE__"|"__SUB__"

%state LEX_OPERATOR

%state LEX_END_BLOCK
%state LEX_POD
%state LEX_QUOTE_LIKE_OPENER_Q, LEX_QUOTE_LIKE_OPENER_QQ, LEX_QUOTE_LIKE_OPENER_QX, LEX_QUOTE_LIKE_OPENER_QW
%state LEX_TRANS_OPENER, LEX_REGEX_OPENER

%state LEX_PACKAGE

// custom lexical states to avoid crossing with navie ones
%state LEX_CUSTOM1, LEX_CUSTOM2, LEX_CUSTOM3, LEX_CUSTOM4, LEX_CUSTOM5, LEX_CUSTOM6, LEX_CUSTOM7, LEX_CUSTOM8, LEX_CUSTOM9, LEX_CUSTOM10
%state LEX_PREPARSED_ITEMS

%state LEX_VARIABLE_NAME, LEX_BRACED_VARIABLE_NAME, LEX_VARIABLE_CLOSE_BRACE

%%

<LEX_POD> {
	{POD_END}	{yybegin(YYINITIAL);return POD;}
	{POD_LINE}	{return POD;}
}

^{POD_START} {yybegin(LEX_POD);return POD;}

<LEX_END_BLOCK>{
	[^]+ {return COMMENT_BLOCK;}
}

// inclusive states
{NEW_LINE}   {return TokenType.NEW_LINE_INDENT;}
{WHITE_SPACE}+   {return TokenType.WHITE_SPACE;}

///////////////////////// package definition ///////////////////////////////////////////////////////////////////////////
<LEX_VARIABLE_NAME>{
	"$"										{yybegin(LEX_OPERATOR); return VARIABLE_NAME;}
	{CAPPED_SINGLE_LETTER_VARIABLE_NAME}	{yybegin(LEX_OPERATOR); return VARIABLE_NAME;}
	{SPECIAL_VARIABLE_NAME} 				{yybegin(LEX_OPERATOR); return VARIABLE_NAME;}
	{IDENTIFIER} 							{yybegin(LEX_OPERATOR); return VARIABLE_NAME;}
	{QUALIFIED_IDENTIFIER} 					{return lexQualifiedIdentifier(LEX_OPERATOR, LEX_VARIABLE_NAME);}
}

<LEX_PACKAGE>{
	{QUALIFIED_IDENTIFIER}			{yybegin(YYINITIAL);return PACKAGE;}
}

<LEX_BRACED_VARIABLE_NAME>{
	"{" 							{return LEFT_BRACE;}
	"$" / "}"						{yybegin(LEX_VARIABLE_CLOSE_BRACE);return VARIABLE_NAME;}
	{CAPPED_BRACED_VARIABLE} / "}"	{yybegin(LEX_VARIABLE_CLOSE_BRACE);return VARIABLE_NAME;}
	{SPECIAL_VARIABLE_NAME} / "}" 	{yybegin(LEX_VARIABLE_CLOSE_BRACE);return VARIABLE_NAME;}
	{IDENTIFIER} / "}" 				{yybegin(LEX_VARIABLE_CLOSE_BRACE);return VARIABLE_NAME;}
	{QUALIFIED_IDENTIFIER} / "}"  	{return lexQualifiedIdentifier(LEX_VARIABLE_CLOSE_BRACE, LEX_BRACED_VARIABLE_NAME);}
}

<LEX_VARIABLE_CLOSE_BRACE> "}" {yybegin(LEX_OPERATOR);return RIGHT_BRACE;}

<YYINITIAL,LEX_OPERATOR>{
	"," 	{yybegin(YYINITIAL);return OPERATOR_COMMA;}
	"++" 	{yybegin(YYINITIAL);return OPERATOR_PLUS_PLUS;}
	"--" 	{yybegin(YYINITIAL);return OPERATOR_MINUS_MINUS;}
	";"     {yybegin(YYINITIAL);return SEMICOLON;}
	"{"     {yybegin(YYINITIAL);return LEFT_BRACE;}
	"}"     {yybegin(LEX_OPERATOR);return RIGHT_BRACE;}
	"["     {yybegin(YYINITIAL);return LEFT_BRACKET;}
	"]"     {yybegin(LEX_OPERATOR);return RIGHT_BRACKET;}
	"("     {yybegin(YYINITIAL);return LEFT_PAREN;}
	")"     {yybegin(LEX_OPERATOR);return RIGHT_PAREN;}

	{CORE_PREFIX}"if"	 	{ yybegin(YYINITIAL); return RESERVED_IF;}
	{CORE_PREFIX}"unless"	{ yybegin(YYINITIAL);return RESERVED_UNLESS;}
	{CORE_PREFIX}"while"	{ yybegin(YYINITIAL);return RESERVED_WHILE;}
	{CORE_PREFIX}"until"	{ yybegin(YYINITIAL);return RESERVED_UNTIL;}
	{CORE_PREFIX}"for"	 	{ yybegin(YYINITIAL);return RESERVED_FOR;}
	{CORE_PREFIX}"foreach"	{ yybegin(YYINITIAL);return RESERVED_FOREACH;}

	{END_BLOCK}						{yybegin(LEX_END_BLOCK);return TAG_END;}
	{DATA_BLOCK}					{yybegin(LEX_END_BLOCK);return TAG_DATA;}
}

<LEX_OPERATOR>{
	"**"	{yybegin(YYINITIAL);return OPERATOR_POW;}
	"%=" 	{yybegin(YYINITIAL);return OPERATOR_MOD_ASSIGN;}
	"*=" 	{yybegin(YYINITIAL);return OPERATOR_MUL_ASSIGN;}
	"&&" 	{yybegin(YYINITIAL);return OPERATOR_AND;}
	"&=" 	{yybegin(YYINITIAL);return OPERATOR_BITWISE_AND_ASSIGN;}
	"**=" 	{yybegin(YYINITIAL);return OPERATOR_POW_ASSIGN;}
	"&&="	{yybegin(YYINITIAL);return OPERATOR_AND_ASSIGN;}
	"*" 	{yybegin(YYINITIAL);return OPERATOR_MUL;}
	"%" 	{yybegin(YYINITIAL);return OPERATOR_MOD;}
	"&" 	{yybegin(YYINITIAL);return OPERATOR_BITWISE_AND;}
	">="	{yybegin(YYINITIAL);return OPERATOR_GE_NUMERIC;}
	"<="	{yybegin(YYINITIAL);return OPERATOR_LE_NUMERIC;}
	"=="	{yybegin(YYINITIAL);return OPERATOR_EQ_NUMERIC;}
	"!="	{yybegin(YYINITIAL);return OPERATOR_NE_NUMERIC;}
	"~~"	{yybegin(YYINITIAL);return OPERATOR_SMARTMATCH;}
	"+="	{yybegin(YYINITIAL);return OPERATOR_PLUS_ASSIGN;}
	"-="	{yybegin(YYINITIAL);return OPERATOR_MINUS_ASSIGN;}
	"/=" 	{yybegin(YYINITIAL);return OPERATOR_DIV_ASSIGN;}
	".=" 	{yybegin(YYINITIAL);return OPERATOR_CONCAT_ASSIGN;}
	"x=" 	{yybegin(YYINITIAL);return OPERATOR_X_ASSIGN;}
	"|=" 	{yybegin(YYINITIAL);return OPERATOR_BITWISE_OR_ASSIGN;}
	"^=" 	{yybegin(YYINITIAL);return OPERATOR_BITWISE_XOR_ASSIGN;}
	"<<=" 	{yybegin(YYINITIAL);return OPERATOR_SHIFT_LEFT_ASSIGN;}
	">>=" 	{yybegin(YYINITIAL);return OPERATOR_SHIFT_RIGHT_ASSIGN;}
	"||=" 	{yybegin(YYINITIAL);return OPERATOR_OR_ASSIGN;}
	"//=" 	{yybegin(YYINITIAL);return OPERATOR_OR_DEFINED_ASSIGN;}
	"<=>" 	{yybegin(YYINITIAL);return OPERATOR_CMP_NUMERIC;}
	"<" 	{yybegin(YYINITIAL);return OPERATOR_LT_NUMERIC;}
	">" 	{yybegin(YYINITIAL);return OPERATOR_GT_NUMERIC;}

	"->" 	{yybegin(YYINITIAL); return OPERATOR_DEREFERENCE;}
	"=>" 	{yybegin(YYINITIAL);return OPERATOR_COMMA_ARROW;}

	"=~" 	{yybegin(YYINITIAL);return OPERATOR_RE;}
	"!~" 	{yybegin(YYINITIAL);return OPERATOR_NOT_RE;}

	"<<" 	{yybegin(YYINITIAL);return OPERATOR_SHIFT_LEFT;}
	">>" 	{yybegin(YYINITIAL);return OPERATOR_SHIFT_RIGHT;}

	"||" 	{yybegin(YYINITIAL);return OPERATOR_OR;}
	"?"  	{yybegin(YYINITIAL);return QUESTION;}
	":"  	{yybegin(YYINITIAL);return COLON;}
	"|" 	{yybegin(YYINITIAL);return OPERATOR_BITWISE_OR;}
	"^" 	{yybegin(YYINITIAL);return OPERATOR_BITWISE_XOR;}
	"=" 	{yybegin(YYINITIAL);return OPERATOR_ASSIGN;}
	"+" 	{yybegin(YYINITIAL);return OPERATOR_PLUS;}
	"-" 	{yybegin(YYINITIAL);return OPERATOR_MINUS;}

	"..." 	{yybegin(YYINITIAL);return OPERATOR_HELLIP;}
	".." 	{yybegin(YYINITIAL);return OPERATOR_FLIP_FLOP;}
	"." 	{yybegin(YYINITIAL);return OPERATOR_CONCAT;}
	"/"   	{yybegin(YYINITIAL);return OPERATOR_DIV;}

	"x"   		{yybegin(YYINITIAL);return OPERATOR_X;}
	"not"		{yybegin(YYINITIAL);return OPERATOR_NOT_LP;}
	"and"		{yybegin(YYINITIAL);return OPERATOR_AND_LP;}
	"or"		{yybegin(YYINITIAL);return OPERATOR_OR_LP;}
	"xor"		{yybegin(YYINITIAL);return OPERATOR_XOR_LP;}
	"lt"		{yybegin(YYINITIAL);return OPERATOR_LT_STR;}
	"gt"		{yybegin(YYINITIAL);return OPERATOR_GT_STR;}
	"le"		{yybegin(YYINITIAL);return OPERATOR_LE_STR;}
	"ge"		{yybegin(YYINITIAL);return OPERATOR_GE_STR;}
	"eq"		{yybegin(YYINITIAL);return OPERATOR_EQ_STR;}
	"ne"		{yybegin(YYINITIAL);return OPERATOR_NE_STR;}
	"cmp"		{yybegin(YYINITIAL);return OPERATOR_CMP_STR;}


}

<YYINITIAL>{
	"$$" / [^$\{\w] 	{return startVariableLexing(1,SIGIL_SCALAR);}
	"$"{VARIABLE_NAME} 	{return startVariableLexing(1,SIGIL_SCALAR);}

	"@$" / [^$\{\w] 	{return startVariableLexing(1,SIGIL_ARRAY);}
	"@"{VARIABLE_NAME} 	{return startVariableLexing(1, SIGIL_ARRAY);}

	"$#$" / [^$\{\w] 	{return startVariableLexing(2,SIGIL_SCALAR_INDEX);}
	"$#"{VARIABLE_NAME} {return startVariableLexing(2, SIGIL_SCALAR_INDEX);}

	"%$" / [^$\{\w] 	{return startVariableLexing(1,SIGIL_HASH);}
	"%"{VARIABLE_NAME} 	{return startVariableLexing(1, SIGIL_HASH);}

	"&$" / [^$\{\w] 	{return startVariableLexing(1,SIGIL_CODE);}
	"&"{VARIABLE_NAME} 	{return startVariableLexing(1, SIGIL_CODE);}

	"*$" / [^$\{\w] 	{return startVariableLexing(1,SIGIL_GLOB);}
	"*"{VARIABLE_NAME} 	{return startVariableLexing(1, SIGIL_GLOB);}

	"$"{BRACED_VARIABLE_NAME} 	{return startBracedVariableLexing(1, SIGIL_SCALAR);}
	"@"{BRACED_VARIABLE_NAME} 	{return startBracedVariableLexing(1, SIGIL_ARRAY);}
	"$#"{BRACED_VARIABLE_NAME} 	{return startBracedVariableLexing(2, SIGIL_SCALAR_INDEX);}
	"%"{BRACED_VARIABLE_NAME} 	{return startBracedVariableLexing(1, SIGIL_HASH);}
	"&"{BRACED_VARIABLE_NAME} 	{return startBracedVariableLexing(1, SIGIL_CODE);}
	"*"{BRACED_VARIABLE_NAME} 	{return startBracedVariableLexing(1, SIGIL_GLOB);}

	{NUMBER_BIN}									{yybegin(LEX_OPERATOR);return NUMBER;}
	{NUMBER_HEX}									{yybegin(LEX_OPERATOR);return NUMBER;}
	{NUMBER_INT}? "." {NUMBER_INT} {NUMBER_EXP}?	{yybegin(LEX_OPERATOR);return NUMBER;}
	{NUMBER_INT} ("." {NUMBER_INT})? {NUMBER_EXP}?	{yybegin(LEX_OPERATOR);return NUMBER;}

//	{DQ_STRING}	{yybegin(LEX_OPERATOR);return STRING_IDENTIFIER;}
//	{SQ_STRING} {yybegin(LEX_OPERATOR);return STRING_IDENTIFIER;}
//	{XQ_STRING} {yybegin(LEX_OPERATOR);return STRING_IDENTIFIER;}
//	"\"" 	{return QUOTE_DOUBLE;}
//	"`" 	{return QUOTE_TICK;}
//	"'" 	{return QUOTE_SINGLE;}

	"/"   						{yybegin(LEX_OPERATOR);return startRegexp();}
	{HEREDOC_OPENER}   			{yybegin(LEX_OPERATOR);return parseHeredocOpener();}
	{HEREDOC_OPENER_BACKREF} 	{yybegin(LEX_OPERATOR);return parseHeredocOpenerBackref();}

	{NAMED_UNARY_OPERATORS}		{return OPERATOR_NAMED_UNARY;}

	"!" 						{return OPERATOR_NOT;}
	"+" 						{return OPERATOR_PLUS_UNARY;}
	"-" 						{return OPERATOR_MINUS_UNARY;}
	"~" 						{return OPERATOR_BITWISE_NOT;}
	{PERL_OPERATORS_FILETEST} 	{yypushback(1);return OPERATOR_FILETEST;}

	"@" 	{return SIGIL_ARRAY;}
	"$#" 	{return SIGIL_SCALAR_INDEX;}
	"$" 	{ return SIGIL_SCALAR; }
	"%"		{ return SIGIL_HASH;}
	"*"		{return SIGIL_GLOB;}
	"&"		{return SIGIL_CODE;}
	"\\" 	{return OPERATOR_REFERENCE;}

	{CORE_PREFIX}"my"	{return RESERVED_MY;}
	{CORE_PREFIX}"our"	{return RESERVED_OUR;}
	{CORE_PREFIX}"local"	{return RESERVED_LOCAL;}
	{CORE_PREFIX}"state"	{return RESERVED_STATE;}

	{CORE_PREFIX}"elsif"	 { return  RESERVED_ELSIF;}
	{CORE_PREFIX}"else"	 { return  RESERVED_ELSE;}
	{CORE_PREFIX}"given"	 { return  RESERVED_GIVEN;}
	{CORE_PREFIX}"when"	 { return  RESERVED_WHEN;}
	{CORE_PREFIX}"default"	 { return  RESERVED_DEFAULT;}
	{CORE_PREFIX}"continue"	 { return  RESERVED_CONTINUE;}

	{CORE_PREFIX}"format"	 { return  RESERVED_FORMAT;}
	{CORE_PREFIX}"sub"	 		{ return  RESERVED_SUB;}

	{CORE_PREFIX}"package"	 	{ yybegin(LEX_PACKAGE); return  RESERVED_PACKAGE;}
	{CORE_PREFIX}"use"	 		{ yybegin(LEX_PACKAGE); return  RESERVED_USE;}
	{CORE_PREFIX}"no"	 		{ yybegin(LEX_PACKAGE); return  RESERVED_NO;}
	{CORE_PREFIX}"require"	 	{ yybegin(LEX_PACKAGE); return  RESERVED_REQUIRE;}

	{CORE_PREFIX}"undef"	{ return RESERVED_UNDEF;}

	// special treatment?
	{CORE_PREFIX}"print"	 { return  RESERVED_PRINT;}
	{CORE_PREFIX}"printf"	 { return  RESERVED_PRINTF;}
	{CORE_PREFIX}"say"	 { return  RESERVED_SAY;}

	{CORE_PREFIX}"grep"	 { return  RESERVED_GREP;}
	{CORE_PREFIX}"map"	 { return  RESERVED_MAP;}
	{CORE_PREFIX}"sort"	 { return  RESERVED_SORT;}

	{CORE_PREFIX}"do"	 { return  RESERVED_DO;}
	{CORE_PREFIX}"eval"	 { return  RESERVED_EVAL;}

	{CORE_PREFIX}"goto"	 { return  RESERVED_GOTO;}
	{CORE_PREFIX}"redo"	 { return  RESERVED_REDO;}
	{CORE_PREFIX}"next"	 { return  RESERVED_NEXT;}
	{CORE_PREFIX}"last"	 { return  RESERVED_LAST;}

	{CORE_PREFIX}"return"	 { return  RESERVED_RETURN;}

	{BLOCK_NAMES}	{return BLOCK_NAME;}
	{TAG_NAMES}		{yybegin(LEX_OPERATOR); return TAG;}

	{PERL_VERSION}  		{yybegin(LEX_OPERATOR);return NUMBER_VERSION;}
	{PACKAGE_SHORT} 		{yybegin(LEX_OPERATOR);return PACKAGE;}

	{CORE_PREFIX}"y" {QUOTE_LIKE_SUFFIX} {pullback(1);return RESERVED_Y;}
	{CORE_PREFIX}"tr"{QUOTE_LIKE_SUFFIX} {pullback(2);return RESERVED_TR;}

	{CORE_PREFIX}"qr"{QUOTE_LIKE_SUFFIX} {pullback(2);return RESERVED_QR;}
	{CORE_PREFIX}"qw"{QUOTE_LIKE_SUFFIX} {pullback(2);return RESERVED_QW;}
	{CORE_PREFIX}"qq"{QUOTE_LIKE_SUFFIX} {pullback(2);return RESERVED_QQ;}
	{CORE_PREFIX}"qx"{QUOTE_LIKE_SUFFIX} {pullback(2);return RESERVED_QX;}
	{CORE_PREFIX}"q"{QUOTE_LIKE_SUFFIX}  {pullback(1);return RESERVED_Q;}

	{CORE_PREFIX}"m"{QUOTE_LIKE_SUFFIX}  {pullback(1);return RESERVED_M;}
	{CORE_PREFIX}"s"{QUOTE_LIKE_SUFFIX}  {pullback(1);return RESERVED_S;}

	{IDENTIFIER}			{yybegin(LEX_OPERATOR);return getIdentifierToken();}
	{QUALIFIED_IDENTIFIER} 	{return lexQualifiedIdentifier(LEX_OPERATOR, YYINITIAL);}
}


/* error fallback [^] */
[^]    { return TokenType.BAD_CHARACTER; }
