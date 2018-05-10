#Mohsin Malik 110880864

import ply.lex as lex
import ply.yacc as yacc
import sys
from numbers import Number

usefile = True

if len(sys.argv) < 2:
	usefile = False

def is_number(x):
	return isinstance(x, Number)
	
def is_list(x):
	return type(x) is list
	
def is_str(x):
	return isinstance(x, str)
	
def get_type(x):
	if is_number(x):
		return 1
	elif is_str(x):
		return 2
	elif is_list(x):
		return 3
	else:
		return -1

tokens = [
	'INT', 'REAL', 'BOOLEAN', 'STRING',
	'LBRACKET', 'RBRACKET', 'LPAREN', 'RPAREN',
	'ADD', 'MINUS', 'MULT', 'DIVIDE', 'FDIVIDE', 'MOD', 'POW',
	'IN', 'LT', 'GT', 'EQUAL', 'NEQUAL', 'LTE', 'GTE',
	'NOT', 'AND', 'OR', 'COMMA'
]

t_LBRACKET = r'\['
t_RBRACKET = r'\]'
t_LPAREN = r'\('
t_RPAREN = r'\)'
t_ADD = r'\+'
t_MINUS = r'\-'
t_POW = r'\*\*'
t_MULT = r'\*'
t_FDIVIDE = r'\/\/'
t_DIVIDE = r'\/'
t_MOD = r'\%'
t_IN = r'in'
t_EQUAL = r'\='
t_NEQUAL = r'\<\>'
t_LTE = r'\<\='
t_LT = r'\<'
t_GTE = r'\>\='
t_GT = r'\>'
t_NOT = r'not'
t_AND = r'and'
t_OR = r'or'
t_COMMA = r','

t_ignore = r' '

def t_REAL(t):
	r'\d*\.\d+'
	t.value = float(t.value)
	return t

def t_INT(t):
	r'\d+'
	t.value = int(t.value)
	return t
	
def t_BOOLEAN(t):
	r'(True|False)'
	if t.value == "True":
		t.value = True
	else:
		t.value = False
	return t
	
def t_STRING(t):
	r"\"([^\\\"]|\\.)*\"|'([^\\']|\\.)*'"
	t.value = t.value[1:len(t.value) - 1];
	return t
	
def t_error(t):
	print("SYNTAX ERROR")
	t.lexer.skip(len(t.value))

lexer = lex.lex()

precedence = (
	("left", "OR"),
	("left", "AND"),
	("left", "NOT"),
	("left", "GT", "GTE", "LT", "LTE", "EQUAL", "NEQUAL"),
	("left", "IN"),
	("left", "ADD", "MINUS"),
	("left", "FDIVIDE"),
	("left", "MOD"),
	("left", "MULT", "DIVIDE"),
	("right", "POW"),
	("right", "UMINUS")
)

def p_statement(p):
	'''
	statement : expression
	'''
	if p[1] != None:
		if isinstance(p[1], str):
			print("'" + p[1] + "'")
		else:
			print(p[1])

def p_expression(p):
	'''
	expression : number
			   | BOOLEAN
			   | STRING
			   | list
			   | empty
	'''
	p[0] = p[1]
	
def p_index(p):
	'''
	expression : expression LBRACKET number RBRACKET
	'''
	try:
		p[0] = p[1][p[3]]
	except IndexError:
		print("SEMANTIC ERROR")
	except TypeError:
		print("SEMANTIC ERROR")
		
def p_expression_2(p):
	'''
	expression : LPAREN expression RPAREN
	'''
	p[0] = p[2]
	
def p_logic(p):
	'''
	expression : NOT expression
			   | expression AND expression
			   | expression OR expression
	'''
	if p[1] == "not":
		if is_list(p[2]) or is_str(p[2]):
			print("SEMANTIC ERROR")
		else:
			p[0] = not p[2]
	else:
		if is_list(p[1]) or is_list(p[3]) or is_str(p[1]) or is_str(p[3]):
			print("SEMANTIC ERROR")
		else:
			if p[2] == "and":
				p[0] = p[1] and p[3]
			elif p[2] == "or":
				p[0] = p[1] or p[3]
	
def p_comparison(p):
	'''
	expression : expression GT expression
			   | expression GTE expression
			   | expression LT expression
			   | expression LTE expression
			   | expression EQUAL expression
			   | expression NEQUAL expression
	'''
	if not is_list(p[1]) and not is_list(p[3]):
		try:
			if p[2] == ">":
				p[0] = p[1] > p[3]
			elif p[2] == ">=":
				p[0] = p[1] >= p[3]
			elif p[2] == "<":
				p[0] = p[1] < p[3]
			elif p[2] == "<=":
				p[0] = p[1] <= p[3]
			elif p[2] == "==":
				p[0] = p[1] == p[3]
			elif p[2] == "<>":
				p[0] = not p[1] == p[3]
		except TypeError:
			print("SEMANTIC ERROR")
	else:
		print("SEMANTIC ERROR")
		
def p_ops(p):
	'''
	expression : expression ADD expression
			   | expression MINUS expression
			   | expression MULT expression
			   | expression DIVIDE expression
			   | expression FDIVIDE expression
			   | expression POW expression
			   | expression MOD expression
			   | expression IN expression
	'''
	if p[2] == "+":
		if get_type(p[1]) == get_type(p[3]):
			p[0] = p[1] + p[3]
		else:
			print("SEMANTIC ERROR")
	elif p[2] == "in":
		try:
			p[0] = p[1] in p[3]
		except TypeError:
			print("SEMANTIC ERROR")
	else:
		if is_number(p[1]) and is_number(p[3]):
			if p[2] == "-":
				p[0] = p[1] - p[3]
			elif p[2] == "*":
				p[0] = p[1] * p[3]
			elif p[2] == "/":
				if p[3] == 0:
					print("SEMANTIC ERROR")
				else:
					p[0] = p[1] / p[3]
			elif p[2] == "//":
				if p[3] == 0:
					print("SEMANTIC ERROR")
				else:
					p[0] = p[1] // p[3]
			elif p[2] == "**":
				p[0] = p[1] ** p[3]
			elif p[2] == "%":
				p[0] = p[1] % p[3]
		else:
			print("SEMANTIC ERROR")
		
def p_uminus(p):
	'expression : MINUS expression %prec UMINUS'
	try:
		p[0] = -p[2]
	except TypeError:
		print("SEMANTIC ERROR")
		
def p_list(p):
	'''
	list : LBRACKET RBRACKET
		 | LBRACKET innerlist RBRACKET
	'''
	if p[2] == "]":
		p[0] = []
	else:
		p[0] = p[2]
		
def p_innerlist(p):
	'''
	innerlist : innerlist COMMA expression
			  | expression
	'''
	if len(p) == 2:
		p[0] = [p[1]]
	else:
		p[0] = p[1] + [p[3]]
	
def p_number(p):
	'''
	number : INT
		   | REAL
	'''
	p[0] = p[1]
	
def p_empty(p):
	'''
	empty :
	'''
	p[0] = None
	
def p_error(p):
	print("SYNTAX ERROR")
	while True:
		tok = parser.token()
		if not tok:
			break;
	
parser = yacc.yacc()

if usefile == True:
	filename = sys.argv[1]

	with open(filename) as f:
		content = f.readlines()
		
	content = [x.strip() for x in content]

	for line in content:
		parser.parse(line)
else:
	while True:
		try:
			s = input("")
		except EOFError:
			break
		parser.parse(s)