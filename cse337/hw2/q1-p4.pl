use warnings;
use strict;

open(my $file, "passwd.txt") or die "Can't open file passwd.txt";

my $userid = 0;
my $groupid = -1;

while(my $line = <$file>) {
	chomp($line);
	my @words = split(":", $line);
	my $uid = $words[2];
	$groupid = $words[3];
	if($userid < $uid) {
		$userid = $uid;
	}
}

close $file;

$userid++;

open($file, ">>", "passwd.txt") or die "Can't open file passwd.txt";

print "Enter username: ";
my $username = <STDIN>;
print "Enter password: ";
my $password = <STDIN>;
print "Enter name: ";
my $name = <STDIN>;
print "Enter home directory: ";
my $home_dir = <STDIN>;
print "Enter shell: ";
my $shell = <STDIN>;
chomp($username);
chomp($password);
chomp($name);
chomp($home_dir);
chomp($shell);

print $file "\n$username:$password:$userid:$groupid:$name:$home_dir:$shell";
close $file;