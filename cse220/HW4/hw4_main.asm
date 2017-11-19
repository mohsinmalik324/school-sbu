.text

.globl main

main:
	li $a0 0xffff0000
	li $a1 4
	li $a2 4
	li $a3 0 # r1
	
	li $t0 0 # c1
	li $t1 3 # r2
	li $t2 3 # c2
	
	addi $sp $sp -12
	sw $t2 8($sp)
	sw $t1 4($sp)
	sw $t0 0($sp)
	
	jal start_game
	
	addi $sp $sp 12

	move $t0 $v0
	li $v0 10
	syscall

.data

.include "hw4.asm"