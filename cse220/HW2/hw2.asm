
##############################################################
# Homework #2
# name: Mohsin Malik
# sbuid: 100880864
##############################################################
.text

##############################
# PART 1 FUNCTIONS
##############################

# a0 - address of the string
# a1 - toReplace char
# a2 - replaceWith char
replace1st:
	blt $a1 0 replace1st_err # check if a1 is less than 0
	blt $a2 0 replace1st_err # check if a2 is less than 0
	bgt $a1 127 replace1st_err # check if a1 is greater than 127
	bgt $a2 127 replace1st_err # check if a2 is greater than 127
	
	# t0 - current address in the string
	# t1 - the current char data
	move $t0 $a0 # t0 = $a0
	
replace1st_loop:
	lbu $t1 0($t0) # load char from memory at t0
	beq $t1 $a1 replace1st_found # check if t1 is equal to the char we are looking for
	beqz $t1 replace1st_not_found # check if t1 is equal to 0 (null terminator)
	addi $t0 $t0 1 # go to the next
	j replace1st_loop
	
replace1st_found:
	sb $a2 0($t0) # store the replacement char into the address of t0
	addi $t0 $t0 1 # add 1 to the address
	move $v0 $t0 # set as the return data
	jr $ra
	
replace1st_not_found:
	li $v0 0
	jr $ra
	
replace1st_err:
	li $v0 -1
	jr $ra

# a0 - Starting address of array
# a1 - startIndex
# a2 - endIndex
# a3 - length
printStringArray:
	blt $a3 1 printStringArray_err # a3 < 1
	blt $a1 0 printStringArray_err # a1 < 0
	blt $a2 0 printStringArray_err # a2 < 0
	bge $a1 $a3 printStringArray_err # a1 >= a3
	bge $a2 $a3 printStringArray_err # a2 >= a3
	blt $a2 $s3 printStringArray_err # a2 < a1
	
	# t0 - the address (index) of the array we are at
	# t1 - our counter for the following loop
	
	move $t0 $a0 # t0 = a0
	li $t1 0 # t1 = 0
	
printStringArray_loop:
	bge $t1 $a1 printStringArray_loop_done # t1 >= a1
	addi $t0 $t0 4 # t0 = t0 + 4
	addi $t1 $t1 1 # t1 = t1 + 1
	j printStringArray_loop
	
printStringArray_loop_done:
	# t2 - save the starting address of the array incase we need it later

	move $t1 $a1 # t1 = a1
	move $t2 $a0 # t2 = a0
	
printStringArray_loop2:
	bgt $t1 $a2 printStringArray_loop2_done # t1 > a2
	lw $a0 0($t0) # load word from t0 into a0
	li $v0 4 # v0 = 4
	syscall # print string
	
	la $a0 newline # print newline twice
	syscall
	syscall
	
	addi $t1 $t1 1 # t1 = t1 + 1
	addi $t0 $t0 4 # t0 = t0 + 4
	
	j printStringArray_loop2
	
printStringArray_loop2_done:
	sub $v0 $a2 $a1 # v0 = a2 - a1
	addi $v0 $v0 1 # v0 = v0 + 1

	jr $ra
	
printStringArray_err:
	li $v0 -1 # v0 = -1
	jr $ra

# a0 - starting address of byte array
verifyIPv4Checksum:
	# t0 - an address (element) of the byte array
	# t1 - data loaded from memory
	# t7 - int variable to mult
	
	move $t0 $a0 # t0 = a0
	addi $t0 $t0 3 # t0 = t0 + 3 - get the address of byte which has the header length field
	lbu $t1 0($t0) # t0 -> t1 - load data at address in t0 to t1
	andi $t1 $t1 0xf # isolate the header length bits
	
	li $t7 4 # t7 = 4
	mult $t1 $t7 # t1 * 4 - mult by 4 to get number of bytes
	mflo $t1 # get the result of the above mult
	
	
	# t0 - the address we are currently located at. we will increment this by 2 to get each half word
	# t1 - our header length in bytes
	# t2 - each halfword we load
	# t3 - checksum
	# t4 - 65536
	# t5 - used to fix end around carries
	# t7 - counter for loop below
	
	li $t4 65536
	li $t3 0 # t3 = 0
	move $t0 $a0 # t0 = a0 - go back to start of array
	li $t7 0 # t7 = 0
	
verifyIPv4Checksum_loop:
	bge $t7 $t1 verifyIPv4Checksum_loop_done # t7 >= t1
	
	lhu $t2 0($t0) # t0 -> t2 - load the halfword
	add $t3 $t3 $t2 # t3 = t3 + t2
	#bge $t3 $t4 verifyIPv4Checksum_eac # t3 >= 65536
	
#verifyIPv4Checksum_eac_done:
	
	addi $t0 $t0 2 # t0 = t0 + 2 - go to next halfword
	addi $t7 $t7 2 # t7 = t7 + 2

	j verifyIPv4Checksum_loop
	
verifyIPv4Checksum_eac:
	andi $t5 $t3 0xffff0000 # isolate the carry bits
	srl $t5 $t5 16 # shift them over 16 bits
	andi $t3 $t3 0xffff # isolate the whole bits
	add $t3 $t3 $t5 # t3 = t3 + t5

	j verifyIPv4Checksum_eac_done
	
verifyIPv4Checksum_loop_done:

	bge $t3 $t4 verifyIPv4Checksum_eac
	
verifyIPv4Checksum_eac_done:

	# t3 - checksum
	not $t3 $t3 # flip the bits
	andi $t3 $t3 0xffff
	move $v0 $t3 # v0 = t3

	jr $ra

##############################
# PART 2 FUNCTIONS
##############################

# a0 - starting address of packet array
# a1 - length of array
# a2 - address where we should start storing the payload
extractData:
	# s0 - starting address of packet array
	# s1 - length of array
	# s2 - address where we should start storing the payload
	# s3 - counter for loop
	# s4 - current starting address of current element
	# s5 - current address of the element
	# s6 - total number of bytes written
	
	addi $sp $sp -32 # sp = sp - 12 - allocate space for 3 registers on the stack
	sw $s0 28($sp) # store the registers on the stack
	sw $s1 24($sp)
	sw $s2 20($sp)
	sw $s3 16($sp)
	sw $s4 12($sp)
	sw $s5 8($sp)
	sw $s6 4($sp)
	sw $ra 0($sp)
	
	move $s0 $a0 # s0 = a0
	move $s1 $a1 # s1 = a1
	move $s2 $a2 # s2 = a2
	li $s3 0 # s3 = 0
	move $s4 $s0 # s4 = s0
	li $s6 0 # s6 = 0
	
extractData_loop:
	bge $s3 $s1 extractData_loop_done # s3 >= s1 - check if loop needs to end
	
	move $s5 $s4 # s5 = s4
	move $a0 $s5 # a0 = a5
	jal verifyIPv4Checksum # verify the checksum
	
	bnez $v0 extractData_checksum_fail # v0 != 0 - check if the checksum failed verification
	
	# t0 - length of the payload
	# t1 - our counter for loop2
	# t2 - loading/storing data
	
	lhu $t0 0($s5) # s5 -> t0 - load the total length field
	addi $t0 $t0 -20 # t0 = t0 - 20 - length of the packet payload
	addi $s5 $s5 20 # s5 = s5 + 20 - get the starting address of the payload
	
	li $t1 0 # t1 = 0 - our counter for the next loop
	
extractData_loop2:
	bge $t1 $t0 extractData_loop2_done # t1 >= t0 - check if our loop should end
	
	lbu $t2 0($s5) # s5 -> t2
	sb $t2 0($s2) # t2 -> s2
	
	addi $t1 $t1 1 # t1 = t1 + 1 - add 1 to counter
	addi $s5 $s5 1 # s5 = s5 + 1 - move 1 byte forward in payload
	addi $s2 $s2 1 # s2 = s2 + 1 - move 1 byte forward in return array
	
	j extractData_loop2
	
extractData_loop2_done:
	
	add $s6 $s6 $t0 # s6 = s6 + t0
	addi $s4 $s4 60 # s4 = s4 + 60 - go to the next element in the array
	addi $s3 $s3 1 # s3 = s3 + 1 - increment our counter by 1
	
	j extractData_loop
	
extractData_checksum_fail:
	li $v0 -1 # v0 = -1
	move $v1 $s3 # v1 = s3
	j extractData_loop_revive_data

extractData_loop_done:
	li $v0 0 # v0 = 0
	move $v1 $s6 # v1 = s6
	j extractData_loop_revive_data
	
extractData_loop_revive_data:
	# restore all s registers
	lw $s0 28($sp)
	lw $s1 24($sp)
	lw $s2 20($sp)
	lw $s3 16($sp)
	lw $s4 12($sp)
	lw $s5 8($sp)
	lw $s6 4($sp)
	lw $ra 0($sp)
	addi $sp $sp 32

	jr $ra

# a0 - starting address of a string
# a1 - number of bytes in the char array
# a2 - address of array to hold resulting strings
processDatagram:
	ble $a1 0 processDatagram_err # a1 <= 0
	
	# save s registers we use onto the stack
	addi $sp $sp -24
	sw $s5 20($sp)
	sw $ra 16($sp)
	sw $s4 12($sp)
	sw $s2 8($sp)
	sw $s1 4($sp)
	sw $s0 0($sp)

	# s0 - starting address of string
	# s1 - num of bytes in string
	# s2 - address of storage array
	# s4 - number of strings we stored

	move $s0 $a0 # s0 = a0
	move $s1 $a1 # s1 = s1
	move $s2 $a2 # s2 = a2
	li $s4 0 # s4 = 0
	
	# t0 - null terminator char
	# t1 - counter
	# t2 - the address we increment
	
	li $t0 '\0'
	li $t1 0
	move $t2 $s0 # t2 = s0
	
processDatagram_loop2:
	bge $t1 $s1 processDatagram_loop2_done # t1 >= s1
	
	addi $t2 $t2 1 # t2 = t2 + 1
	addi $t1 $t1 1 # t1 = t1 + 1

	j processDatagram_loop2

processDatagram_loop2_done:

	sb $t0 0($t2) # t0 -> t2
	addi $s5 $t2 -1 # s5 = t2 - 1
	
processDatagram_loop:
	move $a0 $s0 # a0 = s0
	li $a1 '\n'
	li $a2 '\0'
	jal replace1st
	
	beqz $v0 processDatagram_loop_done # v0 == 0
	
	move $s0 $v0 # s0 = v0
	addi $s4 $s4 1 # s4 = s4 + 1
	
	j processDatagram_loop

processDatagram_loop_done:
	move $v0 $s4 # v0 = s4
	lbu $s5 0($s5)
	beqz $s5 processDatagram_revive_data
	addi $v0 $v0 1 # v0 = v0 + 1
	
	j processDatagram_revive_data
	
processDatagram_err:
	li $v0 -1
	jr $ra
	
processDatagram_revive_data:
	# restore s registers from stack
	lw $s0 0($sp)
	lw $s1 4($sp)
	lw $s2 8($sp)
	lw $s4 12($sp)
	lw $ra 16($sp)
	lw $s5 20($sp)
	addi $sp $sp 24

	jr $ra

##############################
# PART 3 FUNCTIONS
##############################

printDatagram:
    #Define your code here
    ############################################
    # DELETE THIS CODE. Only here to allow main program to run without fully implementing the function
    li $v0, -555
    ############################################
    jr $ra

#################################################################
# Student defined data section
#################################################################
.data
.align 2  # Align next items to word boundary

#place all data declarations here

