use warnings;
use strict;

sub get_num_words {
	if(scalar @_ != 1) {
		return 0;
	}
	my $string = $_[0];
	chomp($string);
	my $count = 0;
	$count++ while $string =~ /\S+/g;
	return $count;
}

if((scalar @ARGV) == 1) {
	my $filename = $ARGV[0];
	open(my $file, $filename) or die "Can't open file $filename";
	my $wholefile = "";
	my $wordcount = 0;
	my $linecount = 0;
	while(my $line = <$file>) {
		$wordcount += get_num_words($line);
		$linecount++;
		$wholefile = "$wholefile$line";
	}
	close $file;
	my $tmp = $wholefile;
	$tmp =~ s/\n//g;
	my $charcount = length($tmp);
	print "lines: $linecount, words: $wordcount, characters: $charcount\n";
	print "reversed:\n";
	my $rev = reverse($wholefile);
	print "$rev";
} else {
	print "Incorrect usage";
}