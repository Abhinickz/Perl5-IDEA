no warnings qw(once);
local *alias = *$full_path;
use warnings qw(once);

# Copy innocent bystanders back.  Note that we lose
# formats; it seems that Perl versions up to 5.10.0
# have a bug which causes copying formats to end up in
# the scalar slot.  Thanks to Ben Morrow for spotting this.

my $oldglob;

foreach my $slot (qw( SCALAR ARRAY HASH IO ) ) {
    next unless defined *$oldglob{$slot};
    *alias = *$oldglob{$slot};
}
my $sub_ref;

if ($sub_ref) {
    *$full_path = $sub_ref;
}


my $cde = $^];
$cde = $^[;

# The ground of all being.

	*ARG					= *_	;

# Matching.

	*LAST_PAREN_MATCH			= *+	;
	*LAST_SUBMATCH_RESULT			= *^N ;
	*LAST_MATCH_START			= *-{ARRAY} ;
	*LAST_MATCH_END				= *+{ARRAY} ;

# Input.

	*INPUT_LINE_NUMBER			= *.	;
	    *NR					= *.	;
	*INPUT_RECORD_SEPARATOR			= */	;
	    *RS					= */	;

# Output.

	*OUTPUT_AUTOFLUSH			= *|	;
	*OUTPUT_FIELD_SEPARATOR			= *,	;
	    *OFS				= *,	;
	*OUTPUT_RECORD_SEPARATOR		= *\	;
	    *ORS				= *\	;

# Interpolation "constants".

	*LIST_SEPARATOR				= *"	;
	*SUBSCRIPT_SEPARATOR			= *;	;
	    *SUBSEP				= *;	;

# Formats

	*FORMAT_PAGE_NUMBER			= *%	;
	*FORMAT_LINES_PER_PAGE			= *=	;
	my $abc *= 123;
	*FORMAT_LINES_LEFT			= *-	;
	*FORMAT_NAME				= *~	;
	*FORMAT_TOP_NAME			= *^	;
	*FORMAT_LINE_BREAK_CHARACTERS		= *:	;
	*FORMAT_FORMFEED			= *^L	;

# Error status.

	*CHILD_ERROR				= *?	;
	*OS_ERROR				= *!	;
	    *ERRNO				= *!	;
	*OS_ERROR				= *!	;
	    *ERRNO				= *!	;
	*EXTENDED_OS_ERROR			= *^E	;
	*EVAL_ERROR				= *@	;

# Process info.

	*PROCESS_ID				= *$	;
	    *PID				= *$	;
    $abc*$abc;
	*REAL_USER_ID				= *<	;
	    *UID				= *<	;
	*EFFECTIVE_USER_ID			= *>	;
	    *EUID				= *>	;
	*REAL_GROUP_ID				= *(	;
	    *GID				= *(	;
	*EFFECTIVE_GROUP_ID			= *)	;
	    *EGID				= *)	;
	*PROGRAM_NAME				= *0	;

# Internals.

	*PERL_VERSION				= *^V	;
	*OLD_PERL_VERSION			= *]	;
	*ACCUMULATOR				= *^A	;
	*COMPILING				= *^C	;
	*DEBUGGING				= *^D	;
	*SYSTEM_FD_MAX				= *^F	;
	*INPLACE_EDIT				= *^I	;
	*PERLDB					= *^P	;
	*LAST_REGEXP_CODE_RESULT		= *^R	;
	*EXCEPTIONS_BEING_CAUGHT		= *^S	;
	*BASETIME				= *^T	;
	*WARNING				= *^W	;
	*EXECUTABLE_NAME			= *^X	;
	*OSNAME					= *^O	;


&method => 123;
--something => 145;
-something => 123;
$self->method => something;

print <<'!END!';
Test from perl lib
!END!

print <<TEST;
message
TEST

print <<'TEST WITH SPACE !';
message
TEST WITH SPACE !

print <<`TEST WITH SPACE !`;
message
TEST WITH SPACE !

print <<"TEST WITH SPACE !";
message
TEST WITH SPACE !

for(;;){
    print "Here";
}
while(){
    print "Here";
}

sub eval{}
sub if{}
sub while{}

&eval;
&while;
&if;
*eval;

my $abcd = {
    --test => something
};

print sprintf "here we go";

print OF "something";
say OF "something";
printf OF "something";

print OF, "something";
say OF, "something";
printf OF, "something";

print OF() "something";
say OF() "something";
printf OF() "something";

close STDERR;
close (STDERR);
syswrite(STDERR, 'Caught a SIG', 12);
syswrite STDERR, 'Caught a SIG', 12;

use Opcode 1.01, qw(..);

my $panic;
kill 'ABRT', $$ if $panic++;

my $$$_wow;
my $${some};
my $$$^a;
$_;
$$_;
my $ abc;
$ $$abc;

qw/testing/;
qw#testing#;
qw'testing testing';
qw{testing testing};

qr'testing';
qr#testing#;
qr/testing/;
qr{testing};

qx/testing/;
qx'testing';
qx#testing#;
qx{testing};

qq/testing/;
qq'testing';
qq#'testing#;
qq{testing};

q/testing/;
q'testing';
q#testing#;
q{testing};

tr'testing'testing';
tr#testing#testing#;
tr/testing/testing/;
tr{testing}{testing};

y'testing'testing';
y#testing#testing#;
y/testing/testing/;
y{testing}{testing};

m'testing';
m#testing#;
m{testing};
m/testing/;

s'testing'testing';
s#testing#testing#;
s/testing/testing/;
s{testing}{testing};

SOMELABEL: {
    last SOMELABEL;
}

$abc ? $cde : blah();

split /2134/, @array;
m/2342342/x;
s/234234/$$/e;

Foo::Bar;

Foo::bar;

Foo::bar baz;

Foo::Bar->method();

bar Foo::baz;

bar Foo(123);

Foo->bar;
my $self;

my $abc = {
    some => 1
};

my @array;

grep {defined} @array;

print if defined $abc;

$abc->{cde} = 2;

$self->SUPER::method();

Foo::Bar->something;

BEGIN {
    print "something";
}

UNITCHECK {
    print "something";
}

CHECK {
    print "something";
}

INIT {
    print "something";
}

END {
    print "something";
}

AUTOLOAD {
    print "something";
}

DESTROY {
    print "something";
}

$abc = __PACKAGE__->"something";
$abc = 1 and 2;


open (IF, 'something');
open IF, 'something';
open Foo::->method();
open Foo::method();

somesub IF, blah;

print OF(1,2,3);
print sprintf "here we go";
print Foo->method();
print Foo::method();
print Foo::->method();
print method, "text";

my @list = <IF>;
my @list = <Foo::method>;
my @list = <{Foo->method}>;

use if (1 > 2);
use open;

package Foo;

sub bar{
}

package main;

sub somesub
{
}

package Foo::Bar;

sub somesub
{

}

${Foo::Bar::somesub()};
@{Foo::Bar::somesub()};
$#{Foo::Bar::somesub()};
%{Foo::Bar::somesub()};
*{Foo::Bar::somesub()};
&{Foo::Bar::somesub()};

Foo::Bar::somesub;
::somesub;
Foo::Bar::somesub();
::somesub();
somesub;
somesub();

$Foo::Bar::var;
$#Foo::Bar::var;
@Foo::Bar::var;
%Foo::Bar::var;
*Foo::Bar::var;


$$Foo::Bar::var;
$#$Foo::Bar::var;
@$Foo::Bar::var;
%$Foo::Bar::var;
*$Foo::Bar::var;
&$Foo::Bar::var;

${Foo::Bar::var};
$#{Foo::Bar::var};
@{Foo::Bar::var};
%{Foo::Bar::var};
*{Foo::Bar::var};

$Foo::Bar::'var;
$#Foo::'Bar'var;
@Foo'Bar'var;
%Foo::Bar'var;
*Foo'Bar::var;

*isn't = sub{};

$abcd;
@abcd;
%abcd;
*abcd;
$#abcd;

${abcd};
@{abcd};
%{abcd};
*{abcd};
$#{abcd};

$$abcd;
$#$abcd;
&$abcd;
@$abcd;
%$abcd;
*$abcd;

$ARG, ${ARG};
$_, ${_};
@ARG, @{ARG};
@_, @{_};
$LIST_SEPARATOR, ${LIST_SEPARATOR};
$", ${"};
$PROCESS_ID, ${PROCESS_ID};
$PID, ${PID};
$$,# ${$};
$PROGRAM_NAME, ${PROGRAM_NAME};
$0, ${0};
$REAL_GROUP_ID, ${REAL_GROUP_ID};
$GID, ${GID};
$(, ${(};
$EFFECTIVE_GROUP_ID, ${EFFECTIVE_GROUP_ID};
$EGID, ${EGID};
$), ${)};
$REAL_USER_ID, ${REAL_USER_ID};
$UID, ${UID};
$<, ${<};
$EFFECTIVE_USER_ID, ${EFFECTIVE_USER_ID};
$EUID, ${EUID};
$>, ${>};
$SUBSCRIPT_SEPARATOR, ${SUBSCRIPT_SEPARATOR};
$SUBSEP, ${SUBSEP};
$;, ${;};
$a, ${a};
$b, ${b};
%ENV, %{ENV};
$OLD_PERL_VERSION, ${OLD_PERL_VERSION};
$], ${]};
$SYSTEM_FD_MAX, ${SYSTEM_FD_MAX};
$^F, ${^F};
@F, @{F};
@INC, @{INC};
%INC, %{INC};
$INPLACE_EDIT, ${INPLACE_EDIT};
$^I, ${^I};
$^M, ${^M};
$OSNAME, ${OSNAME};
$^O, ${^O};
%SIG, %{SIG};
$BASETIME, ${BASETIME};
$^T, ${^T};
$PERL_VERSION, ${PERL_VERSION};
$^V, ${^V};
$^WIN32_SLOPPY_STAT, ${^WIN32_SLOPPY_STAT};
$EXECUTABLE_NAME, ${EXECUTABLE_NAME};
$^X, ${^X};
$1, ${1};
$MATCH, ${MATCH};
$&, ${&};
$^MATCH, ${^MATCH};
$PREMATCH, ${PREMATCH};
$`, ${`};
$^PREMATCH, ${^PREMATCH};
$POSTMATCH, ${POSTMATCH};
$', ${'};
$^POSTMATCH, ${^POSTMATCH};
$LAST_PAREN_MATCH, ${LAST_PAREN_MATCH};
$+, ${+};
$LAST_SUBMATCH_RESULT, ${LAST_SUBMATCH_RESULT};
$^N, ${^N};
@LAST_MATCH_END, @{LAST_MATCH_END};
@+, @{+};
%LAST_PAREN_MATCH, %{LAST_PAREN_MATCH};
%+, %{+};
@LAST_MATCH_START, @{LAST_MATCH_START};
@-, @{-};
%LAST_MATCH_START, %{LAST_MATCH_START};
%-, %{-};
$LAST_REGEXP_CODE_RESULT, ${LAST_REGEXP_CODE_RESULT};
$^R, ${^R};
$^RE_DEBUG_FLAGS, ${^RE_DEBUG_FLAGS};
$^RE_TRIE_MAXBUF, ${^RE_TRIE_MAXBUF};
$OUTPUT_FIELD_SEPARATOR, ${OUTPUT_FIELD_SEPARATOR};
$OFS, ${OFS};
$,, ${,};
$INPUT_LINE_NUMBER, ${INPUT_LINE_NUMBER};
$NR, ${NR};
$., ${.};
$INPUT_RECORD_SEPARATOR, ${INPUT_RECORD_SEPARATOR};
$RS, ${RS};
$/, ${/};
$OUTPUT_RECORD_SEPARATOR, ${OUTPUT_RECORD_SEPARATOR};
$ORS, ${ORS};
$\, ${\};
$OUTPUT_AUTOFLUSH, ${OUTPUT_AUTOFLUSH};
$|, ${|};
$^LAST_FH, ${^LAST_FH};
$ACCUMULATOR, ${ACCUMULATOR};
$^A, ${^A};
$FORMAT_FORMFEED, ${FORMAT_FORMFEED};
$^L, ${^L};
$FORMAT_PAGE_NUMBER, ${FORMAT_PAGE_NUMBER};
$%, ${%};
$FORMAT_LINES_LEFT, ${FORMAT_LINES_LEFT};
$-, ${-};
$FORMAT_LINE_BREAK_CHARACTERS, ${FORMAT_LINE_BREAK_CHARACTERS};
$:, ${:};
$FORMAT_LINES_PER_PAGE, ${FORMAT_LINES_PER_PAGE};
$=, ${=};
$FORMAT_TOP_NAME, ${FORMAT_TOP_NAME};
$^, ${^};
$FORMAT_NAME, ${FORMAT_NAME};
$~, ${~};
$^CHILD_ERROR_NATIVE, ${^CHILD_ERROR_NATIVE};
$EXTENDED_OS_ERROR, ${EXTENDED_OS_ERROR};
$^E, ${^E};
$EXCEPTIONS_BEING_CAUGHT, ${EXCEPTIONS_BEING_CAUGHT};
$^S, ${^S};
$WARNING, ${WARNING};
$^W, ${^W};
$^WARNING_BITS, ${^WARNING_BITS};
$OS_ERROR, ${OS_ERROR};
$ERRNO, ${ERRNO};
$!, ${!};
%OS_ERROR, %{OS_ERROR};
%ERRNO, %{ERRNO};
%!, %{!};
$CHILD_ERROR, ${CHILD_ERROR};
$?, ${?};
$EVAL_ERROR, ${EVAL_ERROR};
$@, ${@};
$COMPILING, ${COMPILING};
$^C, ${^C};
$DEBUGGING, ${DEBUGGING};
$^D, ${^D};
$^ENCODING, ${^ENCODING};
$^GLOBAL_PHASE, ${^GLOBAL_PHASE};
$^H, ${^H};
%^H, %{^H};
$^OPEN, ${^OPEN};
$PERLDB, ${PERLDB};
$^P, ${^P};
$^TAINT, ${^TAINT};
$^UNICODE, ${^UNICODE};
$^UTF8CACHE, ${^UTF8CACHE};
$^UTF8LOCALE, ${^UTF8LOCALE};
$*, ${*};
$[, ${[};
