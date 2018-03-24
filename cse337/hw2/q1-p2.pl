use warnings;
use strict;

my $filename = "passwd.txt";
my @userids = ();
my %usershsh;

open(my $file, $filename) or die "Could not open file $filename";

while(my $line = <$file>) {
	chomp($line);
	my @words = split(":", $line);
	my $userid = $words[2];
	my $username = $words[0];
	$usershsh{$userid} = $username;
	push(@userids, $userid);
}

my @userids_rs = sort {$a <=> $b} @userids;
@userids_rs = reverse @userids_rs;

print "sorted uids: ";
my $userid;

foreach $userid (@userids_rs) {
	print "$userid ";
}

print "\nusernames: ";

foreach $userid (@userids_rs) {
	print "$usershsh{$userid} ";
}

close $file;

# DONE