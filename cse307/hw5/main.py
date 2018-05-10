#Mohsin Malik 110880864

import copy
from numbers import Number
import sys
import ply.lex as lex
import ply.yacc as yacc

if len(sys.argv) < 2:
	print("Must provide a file")
	sys.exit()

class Node:
	def __init__(self):
		print("init node")

	def evaluate(self):
		return 0

	def execute(self):
		return 0

class NumberNode(Node):
	def __init__(self, v):
		if('.' in v):
			self.value = float(v)
		else:
			self.value = int(v)

	def evaluate(self):
		return self.value
		
class StringNode(Node):
	def __init__(self, v):
		self.value = v[1:len(v) - 1]
		
	def evaluate(self):
		return self.value
		
class BooleanNode(Node):
	def __init__(self, v):
		if v == "True":
			self.value = True
		else:
			self.value = False
			
	def evaluate(self):
		return self.value
		
class ListNode(Node):
	def __init__(self, v):
		self.value = v
		
	def evaluate(self):
		tmp = []
		for node in self.value:
			tmp = tmp + [node.evaluate()]
		return tmp
		
class IndexNode(Node):
	def __init__(self, v, index):
		self.value = v
		self.index = index
	
	def evaluate(self):
		try:
			return self.value.evaluate()[self.index.evaluate()]
		except TypeError:
			sem_error()
		except IndexError:
			sem_error()
		
class VarNode(Node):
	def __init__(self, name):
		self.name = name
		
	def evaluate(self):
		if self.name in names:
			return names[self.name]
		sem_error()
		
class UopNode(Node):
	def __init__(self, op, v):
		self.op = op
		self.value = v
		
	def evaluate(self):
		eval = self.value.evaluate()
		if self.op == "not":
			if is_number(eval) == False:
				sem_error()
			return not eval

class BopNode(Node):
	def __init__(self, op, v1, v2):
		self.v1 = v1
		self.v2 = v2
		self.op = op

	def evaluate(self):
		eval1 = self.v1.evaluate()
		eval2 = self.v2.evaluate()
		if self.op == '+':
			try:
				return eval1 + eval2
			except TypeError:
				sem_error()
		elif self.op == '-':
			if is_number(eval1) == False or is_number(eval2) == False:
				sem_error()
			return eval1 - eval2
		elif self.op == '*':
			if is_number(eval1) == False or is_number(eval2) == False:
				sem_error()
			return eval1 * eval2
		elif self.op == '/':
			if is_number(eval1) == False or is_number(eval2) == False:
				sem_error()
			if eval2 == 0:
				sem_error()
			return eval1 / eval2
		elif self.op == '//':
			if is_number(eval1) == False or is_number(eval2) == False:
				sem_error()
			if eval2 == 0:
				sem_error()
			return eval1 // eval2
		elif self.op == '**':
			if is_number(eval1) == False or is_number(eval2) == False:
				sem_error()
			return eval1 ** eval2
		elif self.op == '%':
			if is_number(eval1) == False or is_number(eval2) == False:
				sem_error()
			return eval1 % eval2
		elif self.op == '>':
			if is_list(eval1) or is_list(eval2):
				sem_error()
			return eval1 > eval2
		elif self.op == '>=':
			if is_list(eval1) or is_list(eval2):
				sem_error()
			return eval1 >= eval2
		elif self.op == '<':
			if is_list(eval1) or is_list(eval2):
				sem_error()
			return eval1 < eval2
		elif self.op == '<=':
			if is_list(eval1) or is_list(eval2):
				sem_error()
			return eval1 <= eval2
		elif self.op == '==':
			if is_list(eval1) or is_list(eval2):
				sem_error()
			return eval1 == eval2
		elif self.op == '<>':
			if is_list(eval1) or is_list(eval2):
				sem_error()
			return not eval1 == eval2
		elif self.op == 'and':
			if is_number(eval1) == False or is_number(eval2) == False:
				sem_error()
			return eval1 and eval2
		elif self.op == 'or':
			if is_number(eval1) == False or is_number(eval2) == False:
				sem_error()
			return eval1 or eval2
		elif self.op == 'in':
			if is_number(eval2):
				sem_error()
			return eval1 in eval2

class PrintNode(Node):
	def __init__(self, v):
		self.value = v

	def execute(self):		
		print(self.value.evaluate())
		
class AssignNode(Node):
	def __init__(self, name, v):
		self.name = name
		self.value = v
		
	def execute(self):
		names[self.name] = self.value.evaluate()
		
class AssignIndexNode(Node):
	def __init__(self, name, index, v):
		self.name = name
		self.index = index
		self.value = v
		
	def execute(self):
		names[self.name][self.index.evaluate()] = self.value.evaluate()
		
class IfNode(Node):
	def __init__(self, v, block):
		self.value = v
		self.block = block
		
	def execute(self):
		if self.value.evaluate() == True:
			self.block.execute()
			
class IfElseNode(Node):
	def __init__(self, v, block, elseblock):
		self.value = v
		self.block = block
		self.elseblock = elseblock
		
	def execute(self):
		if self.value.evaluate() == True:
			self.block.execute()
		else:
			self.elseblock.execute()
			
class WhileNode(Node):
	def __init__(self, v, block):
		self.value = v
		self.block = block
		
	def execute(self):
		while(self.value.evaluate() == True):
			self.block.execute()

class BlockNode(Node):
	def __init__(self, s):
		self.sl = [s]

	def execute(self):
		for statement in self.sl:
			statement.execute()

reserved = {
	'print' : 'PRINT',
	'and' : 'AND',
	'or' : 'OR',
	'not' : 'NOT',
	'in' : 'IN',
	'if' : 'IF',
	'else' : 'ELSE',
	'while' : 'WHILE'
}
			
tokens = [
	'LBRACE', 'RBRACE',
	'LPAREN', 'RPAREN', 'SEMI','LBRACKET','RBRACKET','COMMA',
	'NUMBER','STRING','BOOLEAN',
	'PLUS','MINUS','TIMES','DIVIDE','NAME','EQUALS',
	'FDIVIDE','POW','MOD','GT','GTE','LT','LTE','NEQUALS','EQUALSS'
] + list(reserved.values())

# Tokens
t_LBRACE  = r'\{'
t_RBRACE  = r'\}'
t_LPAREN  = r'\('
t_RPAREN  = r'\)'
t_SEMI  = r';'
t_PLUS	= r'\+'
t_MINUS   = r'-'
t_POW = r'\*\*'
t_TIMES   = r'\*'
t_FDIVIDE = r'//'
t_DIVIDE  = r'/'
t_EQUALSS = r'=='
t_EQUALS = r'='
t_LBRACKET = r'\['
t_RBRACKET = r'\]'
t_COMMA = r','
t_MOD = r'%'
t_NEQUALS = r'<>'
t_GTE = r'>='
t_GT = r'>'
t_LTE = r'<='
t_LT = r'<'

def t_NAME(t):
	r'[A-Za-z][A-Za-z0-9_]*'
	if t.value == "True":
		t.value = BooleanNode(t.value)
		t.type = "BOOLEAN"
	elif t.value == "False":
		t.value = BooleanNode(t.value)
		t.type = "BOOLEAN"
	else:
		t.type = reserved.get(t.value, 'NAME')
	return t

def t_NUMBER(t):
	r'-?\d*(\d\.|\.\d)\d* | \d+'
	try:
		t.value = NumberNode(t.value)
	except ValueError:
		print("Integer value too large %d", t.value)
		t.value = 0
	return t
	
def t_STRING(t):
	r"\"([^\\\"]|\\.)*\"|'([^\\']|\\.)*'"
	t.value = StringNode(t.value)
	return t
	
#def t_BOOLEAN(t):
#	r'(True|False)'
#	t.value = BooleanNode(t.value)
#	return t

# Ignored characters
t_ignore = " \t\n"

def t_error(t):
	syntax_error()
	
# Build the lexer
lex.lex()

# Parsing rules
precedence = (
	('left','OR'),
	('left','AND'),
	('left','NOT'),
	('left','GT','GTE','LT','LTE','EQUALSS','NEQUALS'),
	('left','IN'),
	('left','PLUS','MINUS'),
	('left','FDIVIDE'),
	('left','MOD'),
	('left','TIMES','DIVIDE'),
	('right','POW')
)

names = {}

def p_block(t):
	'''
	block : LBRACE inblock RBRACE
	'''
	t[0] = t[2]

def p_inblock(t):
	'''
	inblock : smt inblock
	'''
	t[0] = t[2]
	t[0].sl.insert(0,t[1])

def p_inblock2(t):
	'''
	inblock : smt
	'''
	t[0] = BlockNode(t[1])
	
def p_smt(t):
	'''
	smt : print_smt
		| assign_smt
		| assign_index_smt
		| if_smt
		| if_else_smt
		| while_smt
	'''
	t[0] = t[1]
	
def p_while_smt(t):
	'''
	while_smt : WHILE LPAREN expression RPAREN block
	'''
	t[0] = WhileNode(t[3], t[5])
	
def p_if_smt(t):
	'''
	if_smt : IF LPAREN expression RPAREN block
	'''
	t[0] = IfNode(t[3], t[5])
	
def p_if_else_smt(t):
	'''
	if_else_smt : IF LPAREN expression RPAREN block ELSE block
	'''
	t[0] = IfElseNode(t[3], t[5], t[7])
	
def p_assign_smt(t):
	'''
	assign_smt : NAME EQUALS expression SEMI
	'''
	t[0] = AssignNode(t[1], t[3])
	
def p_assign_index_smt(t):
	'''
	assign_index_smt : NAME LBRACKET expression RBRACKET EQUALS expression SEMI
	'''
	t[0] = AssignIndexNode(t[1], t[3], t[6])

def p_print_smt(t):
	'''
	print_smt : PRINT LPAREN expression RPAREN SEMI
	'''
	t[0] = PrintNode(t[3])
	
def p_unaop(t):
	'''
	expression : NOT expression
	'''
	t[0] = UopNode(t[1], t[2])

def p_binop(t):
	'''
	expression : expression PLUS expression
			   | expression MINUS expression
			   | expression TIMES expression
			   | expression DIVIDE expression
			   | expression FDIVIDE expression
			   | expression MOD expression
			   | expression POW expression
			   | expression GT expression
			   | expression GTE expression
			   | expression LT expression
			   | expression LTE expression
			   | expression EQUALSS expression
			   | expression NEQUALS expression
			   | expression AND expression
			   | expression OR expression
			   | expression IN expression
			   
	'''
	t[0] = BopNode(t[2], t[1], t[3])

def p_expression(t):
	'''
	expression : num
			   | str
			   | bool
			   | list
	'''
	t[0] = t[1]
	
def p_expression2(t):
	'''
	expression : NAME
	'''
	t[0] = VarNode(t[1])
	
def p_expression3(t):
	'''
	expression : LPAREN expression RPAREN
	'''
	t[0] = t[2]
	
def p_index(t):
	'''
	expression : expression LBRACKET expression RBRACKET
	'''
	t[0] = IndexNode(t[1], t[3])
	
def p_list(t):
	'''
	list : LBRACKET innerlist RBRACKET
		 | LBRACKET RBRACKET
	'''
	if len(t) == 3:
		t[0] = ListNode([])
	else:
		t[0] = ListNode(t[2])
		
def p_innerlist(t):
	'''
	innerlist : innerlist COMMA expression
			  | expression
	'''
	if len(t) == 2:
		t[0] = [t[1]]
	else:
		t[0] = t[1] + [t[3]]

def p_num(t):
	'''
	num : NUMBER
	'''
	t[0] = t[1]
	
def p_str(t):
	'''
	str : STRING
	'''
	t[0] = t[1]
	
def p_bool(t):
	'''
	bool : BOOLEAN
	'''
	t[0] = t[1]

def p_error(t):
	syntax_error()

yacc.yacc()

def sem_error():
	print("SEMANTIC ERROR")
	sys.exit()
	
def syntax_error():
	print("SYNTAX ERROR")
	sys.exit()
	
def is_string(x):
	return isinstance(x, str)
	
def is_number(x):
	return isinstance(x, Number)
	
def is_list(x):
	return type(x) is list
	
def is_node(x):
	return isinstance(x, Node)
	
code = open(sys.argv[1], "r").read()

ast = yacc.parse(code)
ast.execute()