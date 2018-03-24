use warnings;
use strict;

my $filename = "passwd.txt";
my %groupnums;

open(my $file, $filename) or die "Could not open file $filename";

while(my $line = <$file>) {
	chomp($line);
	my @words = split(":", $line);
	my $group = $words[3];
	if(exists $groupnums{$group}) {
		$groupnums{$group} = $groupnums{$group} + 1;
	} else {
		$groupnums{$group} = 1;
	}
}

while(my($k, $v) = each %groupnums) {
	print "group $k has $v users\n";
}

close $file;