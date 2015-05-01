package com.perl5.lang.perl.lexer;

/*
    http://jflex.de/manual.html
    http://www2.cs.tum.edu/projects/cup

*/

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import com.perl5.lang.perl.util.PerlFunctionUtil;
import com.perl5.lang.perl.util.PerlScalarUtil;
import com.perl5.lang.perl.util.PerlHashUtil;
import com.perl5.lang.perl.util.PerlGlobUtil;
import com.perl5.lang.perl.util.PerlArrayUtil;
import com.perl5.lang.perl.util.PerlPackageUtil;

%%

%class PerlLexer
%extends PerlLexerProto
%implements FlexLexer, PerlElementTypes
%unicode
%public

%function advance
%type IElementType

/*
http://perldoc.perl.org/perlop.html#Quote-Like-Operators
// regexp
m qr {}
s tr y {}{}
*/


//%line
//%column
%{
    public void setTokenStart(int position){zzCurrentPos = zzStartRead = position;}
    public int getNextTokenStart(){ return zzMarkedPos;}
    public boolean isLastToken(){ return zzMarkedPos == zzEndRead; }
    public void setState(int newState){ zzLexicalState = newState; }

    public void yybegin_YYINITIAL(){yybegin(YYINITIAL);}


    public void yybegin_LEX_EOF(){yybegin(LEX_EOF);}
    public void yybegin_LEX_POD(){yybegin(LEX_POD);}

    public boolean yystate_LEX_MULTILINE_WAITING(){return yystate() == LEX_MULTILINE_WAITING;}

%}


/*
// Char classes
*/
NEW_LINE = \r?\n
WHITE_SPACE     = [ \t\f]
//EMPTY_LINE = {WHITE_SPACE}*{NEW_LINE}?
EMPTY_SPACE = [ \t\f\r\n]
BAREWORD = [a-zA-Z_][a-zA-Z0-9_]*
ANYWORD = [^ \t\f\r\n]

CHAR_ANY        = .|{NEW_LINE}
LINE            = .*
FULL_LINE       = .*{NEW_LINE}?
QUOTE           = "\"" | "'" | "`"
END_OF_LINE_COMMENT = "#" {FULL_LINE}

PERL_VERSION_CHUNK = [0-9][0-9_]*
PERL_VERSION = "v"?{PERL_VERSION_CHUNK}("." {PERL_VERSION_CHUNK})*
NUMBER = [0-9_]+( "." [0-9_]+ )?

THE_END         = __END__
THE_DATA        = __DATA__

PERL_OPERATORS = "," | "->" | "++" | "--" | "**" | "!" | "~" | "\\" | "+" | "-" | "=~" | "!~" | "*" | "/" | "%" | "x" | "<<" | ">>" | "<" | ">" | "<=" | ">=" | "lt" | "gt" | "le" | "ge" | "==" | "!=" | "<=>" | "eq" | "ne" | "cmp" | "~~" | "&" | "|" | "^" | "&&" | "||" | "//" | ".." | "..." | "?" | ":" | "=" | "+=" | "-=" | "*=" | "not" | "and" | "or" | "xor" | "defined" | "ref" | "scalar" | "exists"

MULTILINE_OPENER_SQ = "<<"{WHITE_SPACE}*\'{BAREWORD}\'
MULTILINE_OPENER_DQ = "<<"{WHITE_SPACE}*\"{BAREWORD}\"
MULTILINE_OPENER_DX = "<<"{WHITE_SPACE}*\`{BAREWORD}\`
MULTILINE_OPENER_DQ_BARE = "<<"{WHITE_SPACE}*{BAREWORD}

POD_OPEN         = \=(pod|head1|head2|head3|head4|over|item|back|begin|end|for|encoding){FULL_LINE}
POD_CLOSE       = \="cut"{FULL_LINE}

VAR_SCALAR_SPECIAL = "$^WARNING_BITS" | "$^WIDE_SYSTEM_CALLS" | "$^UNICODE" | "$^TAINT" | "$^UTF8LOCALE" | "$^RE_TRIE_MAXBUF" | "$^CHILD_ERROR_NATIVE" | "$^ENCODING" | "$^OPEN" | "$^RE_DEBUG_FLAGS" | "$^A" | "$^C" | "$^T" | "$^S" | "$^V" | "$^W" | "$^X" | "$^D" | "$^E" | "$^F" | "$^H" | "$^I" | "$^L" | "$^M" | "$^N" | "$^O" | "$^P" | "$^R" | "$^H" | "$!" | "$\"" | "$#" | "$$" | "$%" | "$&" | "$'" | "$(" | "$)" | "$*" | "$+" | "$," | "$_" | "$-" | "$`" | "$." | "$a" | "$/" | "$0" | "$:" | "$;" | "$<" | "$=" | "$>" | "$?" | "$@" | "$[" | "$\"" | "$]" | "$|" | "$^" | "$~" | "$+" | "$-" | "$_" | "$!" | "$+" | "$-"
VAR_ARRAY_SPECIAL = "@_" | "@!" | "@+" | "@-" | "@^H"
VAR_HASH_SPECIAL = "%!" | "%+" | "%-" | "%^H"

VAR_SCALAR = "$"{BAREWORD}("::"{BAREWORD})*
VAR_HASH = "%"{BAREWORD}("::"{BAREWORD})*
VAR_ARRAY = "@"{BAREWORD}("::"{BAREWORD})*
VAR_GLOB = "*"{BAREWORD}("::"{BAREWORD})*

QUOTE_FUNCTIONS = "qq" | "qx" | "q"
QUOTE_LIST_FUNCTIONS = "qw"
LIST_FUNCTIONS = "sort" | "grep" | "keys" | "values" | "map" | {QUOTE_LIST_FUNCTIONS}
CONTROL_FUNCTIONS = "for" | "foreach" | "do" | "while" | "break" | "continule" | "redo" | "last" | "next" | "exit" | "return"
CONDITION_FUNCTIONS = "if" | "elsif" | "else" | "until"
DEFINITION_FUNCTIONS = "my" | "local" | "our" | "sub" | "package"
INCLUDE_FUNCTIONS = "use" | "require" | "do" | "eval"
FUNCTION_SPECIAL = {INCLUDE_FUNCTIONS} | {DEFINITION_FUNCTIONS} | {CONTROL_FUNCTIONS} | {CONDITION_FUNCTIONS} | {LIST_FUNCTIONS} | {QUOTE_FUNCTIONS}


%xstate LEX_EOF
%xstate LEX_POD

%state LEX_BAREWORD_STRING

%state LEX_MULTILINE_WAITING
%xstate LEX_MULTILINE, LEX_MULTILINE_TOKEN
%{
    public void yybegin_LEX_MULTILINE(){yybegin(LEX_MULTILINE);}
    public void yybegin_LEX_MULTILINE_TOKEN(){yybegin(LEX_MULTILINE_TOKEN);}
    public void yybegin_LEX_MULTILINE_WAITING(){yybegin(LEX_MULTILINE_WAITING);}
%}

%xstate LEX_QUOTE_LIKE_OPENER, LEX_QUOTE_LIKE_CHARS, LEX_QUOTE_LIKE_CLOSER
%{
    public void yybegin_LEX_QUOTE_LIKE_CHARS(){yybegin(LEX_QUOTE_LIKE_CHARS);}
    public void yybegin_LEX_QUOTE_LIKE_OPENER(){yybegin(LEX_QUOTE_LIKE_OPENER);}
    public void yybegin_LEX_QUOTE_LIKE_CLOSER(){yybegin(LEX_QUOTE_LIKE_CLOSER);}
%}

%xstate LEX_QUOTE_LIKE_LIST_OPENER, LEX_QUOTE_LIKE_WORDS, LEX_QUOTE_LIKE_LIST_CLOSER
%{
    public void yybegin_LEX_QUOTE_LIKE_WORDS(){yybegin(LEX_QUOTE_LIKE_WORDS);}
    public void yybegin_LEX_QUOTE_LIKE_LIST_OPENER(){yybegin(LEX_QUOTE_LIKE_LIST_OPENER);}
    public void yybegin_LEX_QUOTE_LIKE_LIST_CLOSER(){yybegin(LEX_QUOTE_LIKE_LIST_CLOSER);}
%}

%%

// inclusive states
{NEW_LINE}   { return processNewLine();}
{WHITE_SPACE}+   {return TokenType.WHITE_SPACE;}
";"     {return processSemicolon();}

{END_OF_LINE_COMMENT}  { return PERL_COMMENT; }

<YYINITIAL>{
    {THE_END}               {processDataOpener(); break;}
    {THE_DATA}               {processDataOpener(); break;}
    {POD_OPEN}               {processPodOpener();break;}
}

/**
    qq qx q
**/
<LEX_QUOTE_LIKE_OPENER>{
    {EMPTY_SPACE}+  {return processQuoteLikeStringSpace();}
    .   {
            IElementType type = processQuoteLikeQuote();
            if( type == null ) // disallowed sharp
                break;
            return type;
        }
}

<LEX_QUOTE_LIKE_CHARS>{
    {CHAR_ANY}   {
          IElementType tokenType = processQuoteLikeChar();
          if( tokenType != null )
                return tokenType;
          break;
        }
}

<LEX_QUOTE_LIKE_CLOSER>{
    .   { popState(); return PERL_QUOTE; }
}

/**
    qw ()
**/
<LEX_QUOTE_LIKE_LIST_OPENER>{
    {EMPTY_SPACE}+  {return processQuoteLikeListSpace();}
    .   {
            IElementType type = processQuoteLikeListQuote();
            if( type == null ) // disallowed sharp
                break;
            return type;
        }
}

<LEX_QUOTE_LIKE_WORDS>{
    {EMPTY_SPACE}+ {return processQuoteLikeListSpace(); }
    {ANYWORD}   {
          IElementType tokenType = processQuoteLikeWord();
          if( tokenType != null )
                return tokenType;
          break;
        }
}

<LEX_QUOTE_LIKE_LIST_CLOSER>{
    .   { popState(); return PERL_QUOTE; }
}

/**
    __DATA__ and __END__ eof seeker
**/
<LEX_EOF>
{
    {FULL_LINE} {
        if( isLastToken() )
            return endDataBlock();
        break;
    }
}

/**
    pod section
**/
<LEX_POD>
{
    {POD_CLOSE} { return endPodBlock(); }
    {FULL_LINE} {
        if( isLastToken() )
        {
            endPodBlock();
        }
        break;
    }
}

/**
    Multiline string
**/

<LEX_MULTILINE>{
    {LINE}  {
        if( isMultilineEnd() || isLastToken())
        {
            return endMultiline();
        }
        break;
    }
    {NEW_LINE}  {
        if( isLastToken() )
        {
            return endMultiline();
        }
        break;
    }
}
<LEX_MULTILINE_TOKEN>{
    .*  {yybegin(YYINITIAL);return PERL_STRING_MULTILINE_END;}
}

///////////////////////// package definition ///////////////////////////////////////////////////////////////////////////

{MULTILINE_OPENER_SQ}   {return processMultilineOpener();}
{MULTILINE_OPENER_DQ}   {return processMultilineOpener();}
{MULTILINE_OPENER_DQ_BARE}   {return processMultilineOpener();}
{MULTILINE_OPENER_DX}   {return processMultilineOpener();}

{QUOTE}         {return processStringOpener();}

"::"            {return PERL_DEPACKAGE;}
"=>"            {return PERL_ARROW_COMMA; }
"{"             {return PERL_LBRACE;}
"}"             {return PERL_RBRACE;}
"["             {return PERL_LBRACK;}
"]"             {return PERL_RBRACK;}
"("             {return PERL_LPAREN;}
")"             {return PERL_RPAREN;}


{NUMBER}        {return PERL_NUMBER;}
{PERL_VERSION}  {return PERL_NUMBER_VERSION;}

///////////////////////////////// PERL VARIABLE ////////////////////////////////////////////////////////////////////////
{VAR_SCALAR_SPECIAL} {return PERL_SCALAR;}
{VAR_SCALAR} {return PERL_SCALAR;}
{VAR_HASH_SPECIAL} {return PERL_HASH;}
{VAR_HASH} {return PERL_HASH;}
{VAR_ARRAY_SPECIAL} {return PERL_ARRAY;}
{VAR_ARRAY} {return PERL_ARRAY;}
{VAR_GLOB} {return PERL_GLOB;}
"@" {return PERL_SIGIL_ARRAY;}
"%" {return PERL_SIGIL_HASH;}
"$" {return PERL_SIGIL_SCALAR;}

{QUOTE_FUNCTIONS} {return processQuoteLikeStringOpener();}
{QUOTE_LIST_FUNCTIONS} {return processQuoteLikeListOpener();}
{PERL_OPERATORS}    {return PERL_OPERATOR;}
{FUNCTION_SPECIAL} {return PERL_KEYWORD;}

<LEX_BAREWORD_STRING>
{
    {BAREWORD} { yybegin(YYINITIAL); return PERL_STRING_CONTENT;}
}

{BAREWORD} { return PERL_BAREWORD;}

/* error fallback [^] */
[^]    { return TokenType.BAD_CHARACTER; }
