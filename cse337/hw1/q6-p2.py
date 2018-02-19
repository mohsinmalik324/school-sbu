lst = list(filter(lambda x: x % 2 == 0, list(range(1, 11))))
lst = list(map(lambda x: x ** 2, lst))
print(lst)