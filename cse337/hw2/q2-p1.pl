use warnings;
use strict;

sub sort_lines {
	if((scalar @_) != 1) {
		return;
	}
	my $str = $_[0];
	my @lines = split("\n", $str);
	@lines = sort {length($a) <=> length($b)} @lines;
	return join("\n", @lines);
}

if((scalar @ARGV) == 1) {
	my $filename = $ARGV[0];
	open(my $file, $filename) or die "Can't open file $filename";
	my $str = "";
	while(my $line = <$file>) {
		$str = "$str$line";
	}
	print sort_lines($str);
	close($file);
} else {
	print "Incorrect usage";
}