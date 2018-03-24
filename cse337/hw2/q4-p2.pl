use warnings;
use strict;

print "What string do you want to search for?\n";
my $str = <STDIN>;
chomp($str);

opendir my $dir, "." or die "Can't open directory";
my @files = readdir $dir;
closedir $dir;

foreach my $filename (@files) {
	if(!(-d $filename)) {
		open(my $file, $filename) or die "Can't open file $filename";
		if(grep{/$str/} <$file>) {
			print "Found \"$str\" in file $filename............";
			if(-e $filename) {
				print "e";
			}
			if(-r $filename) {
				print "r";
			}
			if(-w $filename) {
				print "w";
			}
			if(-T $filename) {
				print "T";
			}
			print "\n";
		}
		close $file;
	}
}