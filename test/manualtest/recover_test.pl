# Statements recovery tests

# recover on semicolon
$a = ��������� {something braced is here}
$b = 123;   # <= recover till now
$a = 123;   # this one should be parsed ok

# recover on closing brace
{
    $a = ��������� {something braced is here}
    $b = 123
}
$a = 123;   # this one should be parsed ok

# recover on closing regex delimiter
s/someting/$a = ��������� {something braced is here}/ex;
$a = 123;   # this one should be parsed ok

# recover on named block
$a = ��������� {something braced is here}
BEGIN{
    $a = 123;   # this one should be parsed ok
}

# recover on package keyword
$a = ��������� {something braced is here}
package main;  # this one should be parsed ok

# recover on use keyword
$a = ��������� {something braced is here}
use warnings;  # this one should be parsed ok

# recover on no keyword
$a = ��������� {something braced is here}
no warnings;  # this one should be parsed ok

# recover on sub keyword
$a = ��������� sub {somethingbad;}
sub abc{$b = 123;};  # this one should be parsed ok

# recover on sub keyword
$a = ��������� sub {somethingbad;}
sub CORE::abc{$b = 123;};  # this one should be parsed ok

# recover on sub keyword
$a = ��������� sub {somethingbad;}
sub Foo::abc{$b = 123;};  # this one should be parsed ok

# recover on if compound
$a = ��������� if $something {something braced is here}
if(1){$b = 123;};  # this one should be parsed ok

# recover on unless compound
$a = ��������� unless $something {something braced is here}
unless(1){$b = 123;};  # this one should be parsed ok

# recover on given compound
$a = ��������� given $something {something braced is here}
given($a){$b = 123;};  # this one should be parsed ok

# recover on while compound
$a = ��������� while $something {something braced is here}
while($a){$b = 123;};  # this one should be parsed ok

# recover on until compound
$a = ��������� until $something {something braced is here}
until($a){$b = 123;};  # this one should be parsed ok

# recover on for compound
$a = ��������� if @ARGV {something braced is here}
for $a (@ARGV){$b = 123;};  # this one should be parsed ok

# recover on for compound
$a = ��������� if @ARGV {something braced is here}
for($a = 1; $a < 100; $a++){$b = 123;};  # this one should be parsed ok

# recover on foreach compound
$a = ��������� if @ARGV {something braced is here}
foreach $a (@ARGV){$b = 123;};  # this one should be parsed ok

# recover on foreach compound
$a = ��������� if @ARGV {something braced is here}
foreach($a = 1; $a < 100; $a++){$b = 123;};  # this one should be parsed ok

# recover on when compound
$a = ��������� when $something {something braced is here}
when($a){$b = 123;};  # this one should be parsed ok

# recover on default compound
$a = ��������� if $something {something braced is here}
default{$b = 123;};  # this one should be parsed ok
