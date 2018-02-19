def check_password(password):
	n = len(password)
	if n < 8:
		return 1
		
	if not check_has_letter(password):
		return 2
		
	if not check_has_num(password):
		return 3
		
	if not check_has_special(password):
		return 4
		
	if not check_chars_not_cons(password):
		return 5
		
	if not check_no_increment(password):
		return 6
		
	if not check_dist_chars(password):
		return 7
		
	return 0
	
def check_dist_chars(password):
	chars = set()
	n = len(password)
	for c in password:
		chars.add(c)
	if len(chars) < n / 2:
		return False
	return True
		
def check_chars_not_cons(password):
	n = len(password)
	for i in range(0, n - 1):
		c1 = password[i]
		c2 = password[i + 1]
		if c1 == c2:
			return False
	return True
	
def check_no_increment(password):
	n = len(password)
	for i in range(0, n - 2):
		c1 = ord(password[i])
		c2 = ord(password[i + 1])
		c3 = ord(password[i + 2])
		if c2 == c1 + 1 and c3 == c1 + 2:
			return False
	return True
	
def check_has_num(password):
	for c in password:
		if is_number(c):
			return True
	return False
	
def check_has_letter(password):
	for c in password:
		if is_letter(c):
			return True
	return False
	
def check_has_special(password):
	for c in password:
		if is_special(c):
			return True
	return False
	
def is_number(c):
	ascii = ord(c)
	return ascii >= 48 and ascii <= 57
	
def is_letter(c):
	ascii = ord(c)
	return (ascii >= 65 and ascii <= 90) or (ascii >= 97 and ascii <= 122)
	
def is_special(c):
	return not is_number(c) and not is_letter(c)
	
result = 1
while result != 0:
	result = check_password(input("Input Password: "))