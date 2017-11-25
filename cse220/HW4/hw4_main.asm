.text

.globl main

main:
	li $t1 0xffff0000
	li $t2 2
	li $t4 4
	li $t8 8
	
	sh $t8 0($t1)
	sh $t8 2($t1)
	sh $t2 4($t1)
	sh $t2 6($t1)
	sh $t4 8($t1)
	sh $t2 10($t1)
	sh $t2 12($t1)
	
	#li $v0 10
	#syscall

	li $a0 0xffff0000
	li $a1 2
	li $a2 7
	li $a3 0 # row
	li $t0 0 # direction
	
	addi $sp $sp -4
	sw $t0 0($sp)
	
	jal merge_row
	
	addi $sp $sp 4

	move $t0 $v0
	li $v0 10
	syscall

.data

.include "hw4.asm"
