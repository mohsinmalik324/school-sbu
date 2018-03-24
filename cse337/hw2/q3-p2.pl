use warnings;
use strict;

sub check {
	my $arr_ref = $_[0];
	my $elem = $_[1];
	foreach my $tmp (@$arr_ref) {
		if(lc $tmp eq lc $elem) {
			return 1;
		}
	}
	return 0;
}

sub conv_to_usd {
	return $_[0] / $_[1];
}

sub conv_from_usd {
	return $_[0] * $_[1];
}

my @currencies = ("usd", "eur", "cad", "inr", "jpy", "vnd", "krw", "btc");

print "Exchangeable currency: usd, eur, cad, inr, jpy, vnd, krw, btc\n";
print "Enter the current currency: ";
my $current = <STDIN>;
chomp($current);
while(!check(\@currencies, $current)) {
	print "We do not trade $current!\n";
	print "Re-enter the current currency: ";
	$current = <STDIN>;
	chomp($current);
}

print "Enter the target currency: ";
my $target = <STDIN>;
chomp($target);
while(!check(\@currencies, $target)) {
	print "We do not trade $target!\n";
	print "Re-enter the target currency: ";
	$target = <STDIN>;
	chomp($target);
}

print "Enter the amount of money: ";
my $money = <STDIN>;
chomp($money);

if($target eq $current) {
	print "$money $current is $money $target.";
} else {
	my %convtable = (eur => .81, cad => 1.29, inr => 65.2, jpy => 105.75, vnd => 22750, krw => 1079.43, btc => .000088);
	my $result;
	if($target eq "usd") {
		$result = conv_to_usd($money, $convtable{$current});
	} elsif($current eq "usd") {
		$result = conv_from_usd($money, $convtable{$target});
	} else {
		$result = conv_to_usd($money, $convtable{$current});
		$result = conv_from_usd($result, $convtable{$target});
	}
	print "$money $current is $result $target.";
}