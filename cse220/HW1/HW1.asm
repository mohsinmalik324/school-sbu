# Homework #1
# Name: Mohsin Malik
# Net ID: moamalik
# SBU ID: 110880864

.data

# include the file with the test case information
.include "Header2.asm" #change this line to test with other inputs

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
	unsupported_str: .asciiz "Unsupported:IPv"
	ipv4_str: .asciiz "IPv4\n"
	comma_str: .asciiz ","
	period_str: .asciiz "."
	plus_str: .asciiz "+"
	equal_str: .asciiz "="
	
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
	blt $v0 0 error
	bgt $v0 255 error
	move $s0 $v0
	
	#load and convert AddressOfIPDest1
	lw $a0 AddressOfIPDest1
	li $v0 84
	syscall
	beq $v1 -1 error
	blt $v0 0 error
	bgt $v0 255 error
	move $s1 $v0
	
	#load and convert AddressOfIPDest2
	lw $a0 AddressOfIPDest2
	li $v0 84
	syscall
	beq $v1 -1 error
	blt $v0 0 error
	bgt $v0 255 error
	move $s2 $v0
	
	#load and convert AddressOfIPDest3
	lw $a0 AddressOfIPDest3
	li $v0 84
	syscall
	beq $v1 -1 error
	blt $v0 0 error
	bgt $v0 255 error
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
	
	move $a0 $s0 # print the ip address in hex
	li $v0 34
	syscall
	
	la $a0 newline # print new line char
	li $v0 4
	syscall
	
	lw $s1 AddressOfPayload # get the starting address of the payload
	move $s2 $s1 # $s2 = $s1
	li $s3 0 # number of bytes in payload
	
	#loop starts
payload_size_loop_start:
	lbu $t0 0($s2)
	beqz $t0 payload_size_loop_done # if this runs loop ends
	addi $s3 $s3 1 # $s3++
	addi $s2 $s2 1 # $s2++
	j payload_size_loop_start
	
payload_size_loop_done:
	lbu $s2 3($s5) # $s2 = header length/version byte
	andi $s2 $s2 0xf # $s2 = header length
	li $t0 4 # multiply the header length by 4 to get number of bytes
	mult $s2 $t0
	mflo $s2
	move $s7 $s2 # save contents for later
	add $s2 $s2 $s3 # $s2 = $s2 + $s3; header length + payload size
	sh $s2 0($s5)
	
	lhu $s2 4($s5) # $s2 = fragement offset and flags
	
	srl $a0 $s2 13 # isolate flag bits and print in binary
	li $v0 35
	syscall
	
	la $a0 comma_str # print comma
	li $v0 4
	syscall
	
	andi $a0 $s2 0x1fff # isolate fragment offset bits and print in binary
	li $v0 35
	syscall
	
	la $a0 newline # print newline
	li $v0 4
	syscall
	
	beqz $s4 bytes_sent_zero
	beq $s4 -1 bytes_sent_negone
	
	# if BytesSent > 0
	li $t0 0x8000
	add $t0 $t0 $s4
	
	j after_bytes_sent_check
	
bytes_sent_zero:
	li $t0 0
	j after_bytes_sent_check
	
bytes_sent_negone:
	li $t0 0x4000
	j after_bytes_sent_check
	
after_bytes_sent_check:
	sh $t0 4($s5) # store new byte 4 information
	
	# $s7 is header length * 4
	# $s1 is address of payload
	
	move $t0 $s1
	add $s2 $s5 $s7 # get address of where the payload should be stored
	move $t1 $s2
	
	# $t0 is address of payload
	# $t1 is address of where payload needs to be stored
	
payload_store_loop_start:
	lbu $t2 0($t0)
	beqz $t2 payload_store_loop_done # if this is true loop is over
	sb $t2 0($t1)
	addi $t0 $t0 1 # $t0++
	addi $t1 $t1 1 # $t1++
	j payload_store_loop_start
	
payload_store_loop_done:
	move $a0 $s5
	li $v0 34
	syscall
	
	la $a0 comma_str
	li $v0 4
	syscall
	
	move $a0 $t1
	li $v0 34
	syscall
	
	la $a0 newline
	li $v0 4
	syscall
	
	# store checksum in $s6 and track the byte we are at with $t2
	li $t2 0
	li $s6 0
	# use $t0 to track which address of the header we are at
	move $t0 $s5
	
	# $t0 = address of header we are at
	# $t2 = byte number
	# $s6 = checksum
	
checksum_loop_start:
	# check if we are at the checksum bytes
	beq $t2 8 checksum_loop_skip
	
	lhu $t1 0($t0)
	
	add $s6 $s6 $t1 # $s6 = $s6 + $t1
	
	addi $t2 $t2 2 # $t2 = $t2 + 2
	addi $t0 $t0 2 # $t0 = $t0 + 2
	beq $t2 $s7 checksum_loop_done
	bgt $t2 $s7 checksum_loop_done

	j checksum_loop_start
	
checksum_loop_skip:
	addi $t2 $t2 2 # $t2 = $t2 + 2
	addi $t0 $t0 2 # $t0 = $t0 + 2
	j checksum_loop_start
	
checksum_loop_done:
	andi $t0 $s6 0xffff0000 # isolate the carry bits
	andi $t1 $s6 0x0000ffff # isolate the whole bits
	srl $t0 $t0 16 # shift carry bits over
	add $s6 $t0 $t1 # add carry and whole
	not $s6 $s6 # not it to get checksum
	sh $s6 8($s5)

	j exit
	
error:
	la $a0 Err_string
	li $v0 4
	syscall
	j exit
	
exit:
	li $v0 10
	syscall
