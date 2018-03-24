use warnings;
use strict;

my $filename = "passwd.txt";

open(my $file, $filename) or die "Could not open file $filename";

while(my $line = <$file>) {
	chomp($line);
	my @words = split(":", $line);
	my $userid = $words[2];
	my $username = $words[0];
	if($userid % 2 == 0) {
		print "$username, $userid\n";
	}
}

close $file;

# DONE