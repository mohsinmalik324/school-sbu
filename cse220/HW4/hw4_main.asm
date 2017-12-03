.text

.globl main

main:
	li $s0 0xffff0000
	li $t2 2
	li $t4 4
	li $t8 8
	li $t9 -1
	
	sh $t2 0($s0)
	sh $t9 2($s0)
	sh $t4 4($s0)
	sh $t9 6($s0)
	
	sh $t9 8($s0)
	sh $t2 10($s0)
	sh $t2 12($s0)
	sh $t9 14($s0)
	
	sh $t9 16($s0)
	sh $t9 18($s0)
	sh $t9 20($s0)
	sh $t9 22($s0)
	
	sh $t9 24($s0)
	sh $t9 26($s0)
	sh $t9 28($s0)
	sh $t9 30($s0)
	
	#li $v0 10
	#syscall
	
	move $a0 $s0
	li $a1 4
	li $a2 4
	li $a3 'D'
	jal user_move
	
	#li $v0 10
	#syscall
	
	move $a0 $s0
	li $a1 4
	li $a2 4
	li $a3 'R'
	jal user_move
	
	#li $v0 10
	#syscall
	
	move $a0 $s0
	li $a1 4
	li $a2 4
	li $a3 'U'
	jal user_move
	
	li $v0 10
	syscall
	
	move $a0 $s0
	li $a1 4
	li $a2 4
	li $a3 'U'
	jal user_move
	
	#li $v0 10
	#syscall
	
	move $a0 $s0
	li $a1 4
	li $a2 4
	li $a3 'R'
	jal user_move

	li $v0 10
	syscall

.data

.include "hw4.asm"
