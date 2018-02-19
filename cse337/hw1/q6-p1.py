def is_prime(num):
	if num == 3:
		return True
	for i in range(3, num):
		if num % i == 0:
			return False
	return True

lst = list(range(2, 101))

lst = list(filter(lambda x: x % 2 != 0, lst))
lst = [x for x in lst if is_prime(x)]
lst.insert(0, 2)

print(lst)