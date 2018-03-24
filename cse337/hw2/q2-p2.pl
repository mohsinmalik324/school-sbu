use warnings;
use strict;

sub limit_width {
	if((scalar @_) != 2) {
		return;
	}
	my $str = $_[0];
	my $maxwidth = $_[1];
	my @lines;
	foreach my $line (split("\n", $str)) {
		my @line_split = split(" ", $line);
		push(@lines, \@line_split);
	}
	for(my $i = 0; $i < (scalar @lines); $i++) {
		my $lineref = $lines[$i];
		if(get_line_length($lineref) > $maxwidth) {
			my @nextline;
			my $nextlineref = \@nextline;
			if($i == (scalar @lines) - 1) {
				push(@lines, $nextlineref);
			} else {
				$nextlineref = $lines[$i + 1];
			}
			while(get_line_length($lineref) > $maxwidth) {
				my $lastword = pop(@$lineref);
				unshift(@$nextlineref, $lastword);
			}
		}
	}
	my $fullstring = "";
	for(my $i = 0; $i < scalar @lines; $i++) {
		my $lineref = $lines[$i];
		my @line = @$lineref;
		my $joined = join(" ", @line);
		$fullstring = "$fullstring$joined";
		if($i != (scalar @lines) - 1) {
			$fullstring = "$fullstring\n";
		}
	}
	return $fullstring;
}

sub get_line_length {
	if((scalar @_) != 1) {
		return 0;
	}
	my $lineref = $_[0];
	my @line = @$lineref;
	my $length = 0;
	foreach my $word (@line) {
		$length += length($word);
	}
	$length += (scalar @line) - 1;
	return $length;
}

if((scalar @ARGV) == 2) {
	my $filename = $ARGV[0];
	my $maxwidth = $ARGV[1];

	open(my $file, $filename) or die "Could not open file";
	my $fullstring = "";
	while(my $line = <$file>) {
		$fullstring = "$fullstring$line";
	}
	close $file;
	print limit_width($fullstring, $maxwidth);
} else {
	print "Invalid use";
}