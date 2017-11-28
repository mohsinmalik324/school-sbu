.text

.globl main

main:
	li $t1 0xffff0000
	li $t2 2
	li $t4 4
	li $t8 8
	
	sh $t2 0($t1)
	sh $t4 2($t1)
	sh $t2 4($t1)
	sh $t8 6($t1)
	
	sh $t4 8($t1)
	sh $t2 10($t1)
	li $t0 16
	sh $t0 12($t1)
	sh $t2 14($t1)
	
	sh $t2 16($t1)
	sh $t4 18($t1)
	sh $t8 20($t1)
	sh $t4 22($t1)
	
	sh $t4 24($t1)
	li $t0 64
	sh $t0 26($t1)
	li $t0 16
	sh $t0 28($t1)
	sh $t0 30($t1)
	
	#li $v0 10
	#syscall
	
	move $a0 $t1
	li $a1 4
	li $a2 4
	jal check_state
	
	move $t0 $v0

	li $v0 10
	syscall

.data

.include "hw4.asm"
