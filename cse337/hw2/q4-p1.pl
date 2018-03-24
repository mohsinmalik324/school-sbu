use warnings;
use strict;

print "Enter file name:\n";
my $filename = <>;
chomp($filename);

if(-e $filename) {
	print "$filename already exists. ";
	if(-d "backup") {
		print "Checking backup directory... already exists\n";
	} else {
		mkdir "backup";
		print "Checking backup directory... backup directory created\n";
	}
	my $lines = 0;
	my @line_backup;
	open(my $file, $filename) or die "Can't open file $filename";
	while(my $line = <$file>) {
		push(@line_backup, $line);
		$lines++;
	}
	close $file;
	my $backup = 1;
	if($lines > 10) {
		print "$filename has more than 10 lines. What to do next?\n";
		print "Enter 'c' to backup the first 10 lines, 'o' to overwrite without creating a backup\n";
		my $response = <STDIN>;
		chomp($response);
		if($response eq "o") {
			$backup = 0;
		}
	} else {
		print "$filename has no more than 10 lines.\n";
	}
	if($backup == 1) {
		print "Ok, old file backed up under backup directory\n";
		my $backupfilename = "backup/backup.txt";
		open(my $backupfile, ">", $backupfilename) or die "Can't open file $filename";
		if((scalar @line_backup) > 10) {
			@line_backup = @line_backup[0..9];
		}
		my $count = 0;
		foreach my $line (@line_backup) {
			if($count == (scalar @line_backup) - 1) {
				chomp($line);
			}
			print $backupfile "$line";
			$count++;
		}
		close $backupfile;
	}
}

open(my $file, ">", $filename) or die "Can't open file $filename";

print $file "Perl is cool!";

close $file;

print "Wrote to file $filename";