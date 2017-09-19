# Homework #1
# Name: Mohsin Malik
# Net ID: moamalik
# SBU ID: 110880864

.data

unsupported_str: .asciiz "Unsupported:IPv"
ipv4_str: .asciiz "IPv4\n"
comma_str: .asciiz ","
period_str: .asciiz "."

# include the file with the test case information
.include "Header1.asm" #change this line to test with other inputs

.align 2
	numargs: .word 0
	AddressOfIPDest3: .word 0
	AddressOfIPDest2: .word 0
	AddressOfIPDest1: .word 0
	AddressOfIPDest0: .word 0
	AddressOfBytesSent: .word 0
	AddressOfPayload: .word 0
	Err_string: .asciiz "ERROR\n"
	newline: .asciiz "\n"
	
# Helper macro for accessing command line arguments via Label
.macro load_args
	sw $a0, numargs
	lw $t0, 0($a1)
	sw $t0, AddressOfIPDest3
	lw $t0, 4($a1)
	sw $t0, AddressOfIPDest2
	lw $t0, 8($a1)
	sw $t0, AddressOfIPDest1
	lw $t0, 12($a1)
	sw $t0, AddressOfIPDest0
	lw $t0, 16($a1)
	sw $t0, AddressOfBytesSent
	lw $t0, 20($a1)
	sw $t0, AddressOfPayload
.end_macro

.text

.globl main

main:
	load_args() #load arguments
	lw $t0 numargs #load word from numargs into t0
	bne $t0 6 error #if t0 isn't 6 display error message
	
	#load and convert AddressOfIPDest0
	lw $a0 AddressOfIPDest0
	li $v0 84
	syscall
	beq $v1 -1 error
	move $s0 $v0
	
	#load and convert AddressOfIPDest1
	lw $a0 AddressOfIPDest1
	li $v0 84
	syscall
	beq $v1 -1 error
	move $s1 $v0
	
	#load and convert AddressOfIPDest2
	lw $a0 AddressOfIPDest2
	li $v0 84
	syscall
	beq $v1 -1 error
	move $s2 $v0
	
	#load and convert AddressOfIPDest3
	lw $a0 AddressOfIPDest3
	li $v0 84
	syscall
	beq $v1 -1 error
	move $s3 $v0
	
	#load and convert AddressOfBytesSent
	lw $a0 AddressOfBytesSent # load the word of BytesSent into $a0
	li $v0 84 # load 84 into $v0
	syscall # call atoi
	beq $v1 -1 error # check if atoi failed
	move $s4 $v0 # load BytesSent into $s4
	slti $t0 $s4 -1 # check if $s4 is less than 1
	beq $t0 1 error # print error if above is true
	li $t1 8191 # load 8191 into $t1
	sgt $t0 $s4 $t1 # check if BytesSent ($s4) is greater than 8191
	beq $t0 1 error # check if $t0 is 1 and if so print error
	beq $s4 -1 bytes_sent_good # check is ByteSent ($s4) is equal to -1
	li $t0 8 # load 8 into $t0
	div $s4 $t0 # do BytesSent/8 or $s4/$t0
	mfhi $t0 # move remainder to $t0
	beq $t0 0 bytes_sent_good # check if the remainder ($t0) is 0
	
	j error
	
bytes_sent_good:
	la $s5 Header # get the starting address of the packet
	lbu $t0 3($s5) # get the byte which stores the version number and header length
	srl $t1 $t0 4 # shift bits in $s1 to the right 4 times to get version number
	beq $t1 4 ipv4 # check if version number is 4
	
	la $a0 unsupported_str # load address of unsupported_str
	li $v0 4 # put 4 in $v0 to print_str
	syscall # print unsupported_str
	
	move $a0 $t1
	li $v0 1 # put 1 in $v0 to print_int
	syscall # print the version number
	
	la $a0 newline # load address of newline
	li $v0 4 # put 4 in $v0 to print_str
	syscall # print newline
	
	# isolate the header length by shifting to the left 28 bits then back to the right 28 bits
	sll $t1 $t0 28
	srl $t1 $t1 28
	
	li $t2 0x40 # load hex-40 into $t2
	add $t1 $t1 $t2 # $t1 = $t1 + $t2
	
	sb $t1 3($s5) # store the new byte into memory
	
	j after_version_check

ipv4:
	la $a0 ipv4_str
	li $v0 4
	syscall
	j after_version_check

after_version_check:
	lbu $a0 2($s5) # load type of service into $a0 and print it
	li $v0 1
	syscall
	
	la $a0 comma_str # prints a comma
	li $v0 4
	syscall
	
	lhu $a0 6($s5) # load identifier into $a0 and print it
	li $v0 1
	syscall
	
	la $a0 comma_str # prints a comma
	li $v0 4
	syscall
	
	lbu $a0 11($s5) # load ttl into $a0 and print it
	li $v0 1
	syscall
	
	la $a0 comma_str # prints a comma
	li $v0 4
	syscall
	
	lbu $a0 10($s5) # load protocol into $a0 and print it
	li $v0 1
	syscall
	
	la $a0 newline # load newline into $a0 and print it
	li $v0 4
	syscall
	
	lbu $a0 15($s5) # load first segment of src ip and print it
	li $v0 1
	syscall
	
	la $a0 period_str
	li $v0 4
	syscall
	
	lbu $a0 14($s5) # load second segment of src ip and print it
	li $v0 1
	syscall
	
	la $a0 period_str
	li $v0 4
	syscall
	
	lbu $a0 13($s5) # load third segment of src ip and print it
	li $v0 1
	syscall
	
	la $a0 period_str
	li $v0 4
	syscall
	
	lbu $a0 12($s5) # load fourth segment of src ip and print it
	li $v0 1
	syscall
	
	la $a0 newline # load newline and print it
	li $v0 4
	syscall
	
	# shift the bits so that we can add it into a single word
	sll $s3 $s3 24
	sll $s2 $s2 16
	sll $s1 $s1 8
	
	add $s0 $s0 $s1 # $s0 = $s0 + $s1
	add $s0 $s0 $s2 # $s0 = $s0 + $s2
	add $s0 $s0 $s3 # $s0 = $s0 + $s3
	
	sw $s0 16($s5) # store the ip into memory
	
	move $a0 $s0
	li $v0 34
	syscall
	
	la $a0 newline
	li $v0 10
	syscall

	j exit
	
error:
	la $a0 Err_string
	li $v0 4
	syscall
	j exit
	
exit:
	li $v0 10
	syscall
