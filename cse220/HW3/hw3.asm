##############################################################
# Homework #3
# name: Mohsin Malik
# sbuid: 100880864
##############################################################

.text

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
	blt $a2 $a1 printStringArray_err # a2 < a1
	
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
	addi $sp $sp -28
	sw $s6 24($sp)
	sw $s5 20($sp)
	sw $ra 16($sp)
	sw $s4 12($sp)
	sw $s2 8($sp)
	sw $s1 4($sp)
	sw $s0 0($sp)

	# s0 - address of string
	# s1 - num of bytes in string
	# s2 - address of storage array
	# s4 - number of strings we stored
	# s6 - starting address of string

	move $s0 $a0 # s0 = a0
	move $s1 $a1 # s1 = s1
	move $s2 $a2 # s2 = a2
	li $s4 0 # s4 = 0
	move $s6 $s0 # s6 = s0
	
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
	beqz $s5 processDatagram_store_addrs
	addi $v0 $v0 1 # v0 = v0 + 1
	
	j processDatagram_store_addrs
	
processDatagram_err:
	li $v0 -1
	jr $ra
	
processDatagram_store_addrs:
	li $t0 1
	sw $s6 0($s2) # s6 -> s2
	addi $s2 $s2 4 # s2 = s2 + 4
	addi $s6 $s6 1 # s6 = s6 + 1
	
processDatagram_loop3:
	bge $t0 $v0 processDatagram_revive_data # t0 >= v0
	
	lbu $t1 0($s6) # s6 -> t1
	beqz $t1 processDatagram_loop3_found_null # t1 == 0
	
	addi $s6 $s6 1 # s6 = s6 + 1
	
	j processDatagram_loop3
	
processDatagram_loop3_found_null:
	addi $t0 $t0 1 # t0 = t0 + 1
	addi $s6 $s6 1 # s6 = s6 + 1
	sw $s6 0($s2) # s6 -> s2
	addi $s2 $s2 4 # s2 = s2 + 4
	addi $s6 $s6 1 # s6 = s6 + 1
	
	j processDatagram_loop3
	
processDatagram_revive_data:
	# restore s registers from stack
	lw $s0 0($sp)
	lw $s1 4($sp)
	lw $s2 8($sp)
	lw $s4 12($sp)
	lw $ra 16($sp)
	lw $s5 20($sp)
	lw $s6 24($sp)
	addi $sp $sp 28

	jr $ra

# a0 - ipv4 packet array
# a1 - number of packets in packet array
# a2 - byte array
# a3 - string array
printDatagram:
	addi $sp $sp -20
	sw $ra 16($sp)
	sw $s3 12($sp)
	sw $s2 8($sp)
	sw $s1 4($sp)
	sw $s0 0($sp)
	
	# s0 - ipv4 packet array
	# s1 - number of packets in packet array
	# s2 - byte array
	# s3 - string array
	
	move $s0 $a0 # s0 = a0
	move $s1 $a1 # s1 = a1
	move $s2 $a2 # s2 = a2
	move $s3 $a3 # s3 = a3
	
	move $a0 $s0
	move $a1 $s1
	move $a2 $s2
	
	jal extractData
	beq $v0 -1 printDatagram_err # v0 == -1
	
	move $a0 $s2 # a0 = s2
	move $a1 $v1 # a1 = v1
	move $a2 $s3 # a2 = a3
	
	jal processDatagram
	beq $v0 -1 printDatagram_err # v0 == -1
	
	move $a0 $s3 # a0 = s3
	li $a1 0 # a0 = 0
	move $a2 $v0 # a2 = v0
	addi $a2 $a2 -1 # a2 = a2 - 1
	move $a3 $v0 # a3 = v0
	
	jal printStringArray
	beq $v0 -1 printDatagram_err # v0 == -1
	
	li $v0 0 # v0 = 0
	
	j printDatagram_done
	
printDatagram_err:
	li $v0 -1
	j printDatagram_done
	
printDatagram_done:
	lw $s0 0($sp)
	lw $s1 4($sp)
	lw $s2 8($sp)
	lw $s3 12($sp)
	lw $ra 16($sp)
	addi $sp $sp 20

	jr $ra
	
# a0 - packet array address
# a1 - size of the packet array (n)
# a2 - starting address of our message
# a3 - size of each packet in the array
extractUnorderedData:
	# save any necessary registers onto the stack
	addi $sp $sp -36
	sw $s7 32($sp)
	sw $s6 28($sp)
	sw $s5 24($sp)
	sw $s4 20($sp)
	sw $s3 16($sp)
	sw $s2 12($sp)
	sw $s1 8($sp)
	sw $s0 4($sp)
	sw $ra 0($sp)

	blt $a1 0 extractUnorderedData_err # a1 < 0
	li $t0 1 # t0 = 1
	beq $a1 $t0 extractUnorderedData_1 # a1 == 1
	j extractUnorderedData_n
	
extractUnorderedData_1:
	move $s0 $a0 # s0 = a0
	jal verifyIPv4Checksum
	beqz $v0 extractUnorderedData_1_cg # v0 == 0
	j extractUnorderedData_1_err

extractUnorderedData_1_cg: # cg = checksum good
	move $a0 $s0 # a0 = s0
	jal getFlags
	li $t0 2 # t0 = 2
	beq $v0 $t0 extractUnorderedData_1_nf # v0 == 2
	beqz $v0 extractUnorderedData_1_flag0 # v0 == 0
	j extractUnorderedData_err
	
extractUnorderedData_1_flag0:
	move $a0 $s0 # a0 = s0
	jal getFragmentOffset
	beqz $v0 extractUnorderedData_1_nf # v0 == 0
	j extractUnorderedData_err
	
extractUnorderedData_1_nf: # nf = not fragmented
	move $a0 $s0 # a0 = s0
	move $s1 $a1 # s1 = a1
	move $a1 $a3 # a1 = a3
	jal checkTotalLength
	beqz $v0 extractUnorderedData_1_tlgtpes # v0 == 0
	j extractUnorderedData_1_err
	
extractUnorderedData_1_tlgtpes: # tlgtpes = total length greater than packet entry size
	# s2 - payload size
	# s3 - header length in bytes
	# s4 - starting address of byte array
	move $a0 $s0 # a0 = s0
	jal calcPayloadSize
	move $s2 $v0 # s2 = v0
	move $a0 $s0 # a0 = s0
	jal getHeaderLength
	move $s3 $v0 # s3 = v0
	li $t0 4 # t0 = 4
	mul $s3 $s3 $t0 # s3 = s3 * 4
	
	move $s4 $a2 # s4 = a2
	move $s5 $a3 # s5 = a3
	
	move $a0 $s0 # a0 = s0
	add $a0 $a0 $s3 # a0 = a0 + s3
	move $a1 $a2 # a1 = a2
	li $a2 0 # a2 = 0
	move $a3 $s2 # a3 = s2
	jal storeMessageIntoArray
	
	move $v1 $v0 # v1 = v0
	li $v0 0 # v0 = 0
	
	j extractUnorderedData_exit
	
extractUnorderedData_n:
	# s0 - counter
	# s1 - packet array
	# s2 - n
	# s3 - byte array
	# s4 - size of each packet element in array
	# s5 - start packet counter
	# s6 - end packet counter
	# s7 - highest stored index
	
	move $s1 $a0 # s1 = a0
	move $s2 $a1 # s2 = a1
	move $s3 $a2 # s3 = a2
	move $s4 $a3 # s4 = a3
	li $s0 0 # s0 = 0
	li $s5 0 # s5 = 0
	li $s6 0 # s6 = 0
	li $s7 -1 # s7 = -1
	
extractUnorderedData_n_loop:
	bge $s0 $s2 extractUnorderedData_n_done # s0 >= $s2
	
	move $a0 $s1 # a0 = s1
	jal verifyIPv4Checksum
	bnez $v0 extractUnorderedData_n_err # v0 != 0
	move $a0 $s1 # a0 = s1
	move $a1 $s4 # a1 = s4
	jal checkTotalLength
	bnez $v0 extractUnorderedData_n_err # v0 != 0
	move $a0 $s1 # a0 = s1
	jal getPacketType
	li $t0 -1 # t0 = -1
	beq $v0 $t0 extractUnorderedData_err # v0 == -1
	beqz $v0 extractUnorderedData_n_loop_start # v0 == 0
	li $t0 2 # t0 = 2
	beq $v0 $t0 extractUnorderedData_n_loop_end # v0 == 2
	j extractUnorderedData_n_loop_after
extractUnorderedData_n_loop_start:
	addi $s5 $s5 1 # s5 = s5 + 1
	j extractUnorderedData_n_loop_after
extractUnorderedData_n_loop_end:
	addi $s6 $s6 1 # s6 = s6 + 1
extractUnorderedData_n_loop_after:
	move $a0 $s1 # a0 = s1
	jal getWhereToStore
	li $t0 -1 # t0 = -1
	beq $v0 $t0 extractUnorderedData_err # v0 == -1
	
	# save index to store on stack (ran out of s registers)
	addi $sp $sp -4
	sw $v0 0($sp)
	
	move $a0 $s1 # a0 = s1
	jal getHeaderLength
	li $t0 4 # t0 = 4
	mul $v0 $v0 $t0 # v0 = v0 * 4 - get the header length in bytes
	
	# save the header length on the stack (ran out of s registers)
	addi $sp $sp -4
	sw $v0 0($sp)
	
	move $a0 $s1 # a0 = s1
	jal calcPayloadSize
	
	# get saved variables from stack
	lw $t0 0($sp) # header length in bytes
	lw $t1 4($sp) # index
	addi $sp $sp 8
	
	add $a0 $s1 $t0 # a0 = s1 + t0 - get starting address of the payload
	move $a1 $s3 # a1 = s3
	move $a2 $t1 # a2 = t1
	move $a3 $v0 # a3 = v0
	jal storeMessageIntoArray
	ble $v0 $s7 extractUnorderedData_n_skip # v0 <= s7
	move $s7 $v0
	
extractUnorderedData_n_skip:
	
	add $s1 $s1 $s4 # s1 = s1 + s4
	addi $s0 $s0 1 # s0 = s0 + 1
	
	j extractUnorderedData_n_loop
	
extractUnorderedData_n_err:
	li $v0 -1 # v0 = -1
	move $v1 $s0 # v1 = s0
	j extractUnorderedData_exit
	
extractUnorderedData_n_done:
	li $t0 1 # t0 = 1
	bne $s5 $t0 extractUnorderedData_err # s5 != 1
	bne $s6 $t0 extractUnorderedData_err # s6 != 1
	li $v0 0 # v0 = 0
	move $v1 $s7 # v1 = s7
	j extractUnorderedData_exit
	
extractUnorderedData_1_err:
	li $v0 -1 # v0 = -1
	li $v1 0 # v1 = 0
	j extractUnorderedData_exit
	
extractUnorderedData_err:
	li $v0 -1
	li $v1 -1
	j extractUnorderedData_exit

extractUnorderedData_exit:
	# restore registers
	lw $ra 0($sp)
	lw $s0 4($sp)
	lw $s1 8($sp)
	lw $s2 12($sp)
	lw $s3 16($sp)
	lw $s4 20($sp)
	lw $s5 24($sp)
	lw $s6 28($sp)
	lw $s7 32($sp)
	addi $sp $sp 36

	jr $ra
	
# a0 - ipv4 packet array
# a1 - number of packets in packet array
# a2 - byte array
# a3 - string array
# a4(on stack) - packetentrysize
printUnorderedDatagram:
	lw $s4 0($sp) # load fifth argument from stack

	addi $sp $sp -24
	sw $ra 20($sp)
	sw $s4 16($sp)
	sw $s3 12($sp)
	sw $s2 8($sp)
	sw $s1 4($sp)
	sw $s0 0($sp)
	
	# s0 - ipv4 packet array
	# s1 - number of packets in packet array
	# s2 - byte array
	# s3 - string array
	# s4 - packetentrysize
	
	move $s0 $a0 # s0 = a0
	move $s1 $a1 # s1 = a1
	move $s2 $a2 # s2 = a2
	move $s3 $a3 # s3 = a3
	
	move $a0 $s0 # a0 = s0
	move $a1 $s1 # a1 = s1
	move $a2 $s2 # a2 = s2
	move $a3 $s4 # a3 = s4
	
	jal extractUnorderedData
	beq $v0 -1 printUnorderedDatagram_err # v0 == -1
	
	move $a0 $s2 # a0 = s2
	move $a1 $v1 # a1 = v1
	move $a2 $s3 # a2 = a3
	
	jal processDatagram
	beq $v0 -1 printUnorderedDatagram_err # v0 == -1
	
	move $a0 $s3 # a0 = s3
	li $a1 0 # a0 = 0
	move $a2 $v0 # a2 = v0
	addi $a2 $a2 -1 # a2 = a2 - 1
	move $a3 $v0 # a3 = v0
	
	jal printStringArray
	beq $v0 -1 printUnorderedDatagram_err # v0 == -1
	
	li $v0 0 # v0 = 0
	
	j printUnorderedDatagram_done
	
printUnorderedDatagram_err:
	li $v0 -1
	j printUnorderedDatagram_done
	
printUnorderedDatagram_done:
	lw $s0 0($sp)
	lw $s1 4($sp)
	lw $s2 8($sp)
	lw $s3 12($sp)
	lw $s4 16($sp)
	lw $ra 20($sp)
	addi $sp $sp 24

	jr $ra
	
	
# a0 - address of the packet
getFlags:
	addi $t0 $a0 5 # t0 = a0 + 5 - move forward to byte 5 which stores the flags
	lbu $t1 0($t0) # t0 -> t1
	srl $v0 $t1 5 # shift by 5 bits to the right to get flags
	jr $ra
	
# a0 - address of the packet
getFragmentOffset:
	addi $t0 $a0 4 # t0 = a0 + 4 - move forward to byte 4
	lhu $t1 0($t0) # t0 -> t1
	andi $v0 $t1 0x1FFF
	jr $ra
	
# a0 - address of the packet
getTotalLength:
	lhu $v0 0($a0) # a0 -> v0
	jr $ra
	
# a0 - address of the packet
getHeaderLength:
	addi $t0 $a0 3 # t0 = a0 + 3
	lbu $t1 0($t0) # t0 -> t1
	andi $v0 $t1 0xF
	jr $ra
	
# a0 - the packet
calcPayloadSize:
	addi $sp $sp -12
	sw $s1 8($sp)
	sw $s0 4($sp)
	sw $ra 0($sp)
	
	move $s0 $a0 # s0 = a0
	
	jal getHeaderLength
	li $t0 4 # t0 = 4
	mul $s1 $v0 $t0 # s1 = v0 * 4
	
	move $a0 $s0
	jal getTotalLength
	sub $v0 $v0 $s1 # v0 = v0 - s1
	
	lw $ra 0($sp)
	lw $s0 4($sp)
	lw $s1 8($sp)
	addi $sp $sp 12
	
	jr $ra
	
# a0 - packet address
# a1 - packetentrysize
checkTotalLength:
	lhu $t0 0($a0) # a0 -> t0
	bgt $t0 $a1 checkTotalLength_gt # t0 > a1
	li $v0 0 # v0 = 0
	jr $ra
	
checkTotalLength_gt:
	li $v0 1 # v0 = 1
	jr $ra
	
# a0 - starting address of message
# a1 - byte array
# a2 - index to start storing
# a3 - message size
storeMessageIntoArray:
	add $a1 $a1 $a2 # a1 = a1 + a2 - move the pointer to where we want to start storing data
	li $t1 0
	
storeMessageIntoArray_loop:
	bge $t1 $a3 storeMessageIntoArray_exit # t1 >= a3
	lbu $t0 0($a0) # a0 -> t0
	sb $t0 0($a1) # t0 -> a1
	addi $a0 $a0 1 # a0 = a0 + 1
	addi $a1 $a1 1 # a1 = a1 + 1
	addi $t1 $t1 1 # t1 = t1 + 1
	j storeMessageIntoArray_loop
	
storeMessageIntoArray_exit:
	move $v0 $t1 # v0 = t1
	add $v0 $v0 $a2 # v0 = v0 + a2
	jr $ra
	
# a0 - packet
getPacketType:
	addi $sp $sp -16
	sw $s2 12($sp)
	sw $s1 8($sp)
	sw $s0 4($sp)
	sw $ra 0($sp)
	
	# s0 - packet
	# s1 - flags
	# s2 - fragment offset
	
	move $s0 $a0 # s0 = a0
	jal getFlags
	move $s1 $v0 # s1 = v0
	move $a0 $s0 # a0 = s0
	jal getFragmentOffset
	move $s2 $v0 # s2 = v0
	
	li $t0 4 # t0 = 4
	beq $s1 $t0 getPacketType_flag4 # s1 == 4
	beqz $s1 getPacketType_flag0 # s1 == 0
	
	j getPacketType_err
	
getPacketType_flag0:
	bnez $s2 getPacketType_end # s2 != 0
	j getPacketType_err
	
getPacketType_flag4:
	beqz $s2 getPacketType_start # s2 == 0
	j getPacketType_middle
	
getPacketType_start:
	li $v0 0 # v0 = 0
	j getPacketType_exit

getPacketType_middle:
	li $v0 1 # v0 = 1
	j getPacketType_exit

getPacketType_end:
	li $v0 2 # v0 = 2
	j getPacketType_exit
	
getPacketType_err:
	li $v0 -1 # v0 = -1
	j getPacketType_exit
	
getPacketType_exit:
	lw $ra 0($sp)
	lw $s0 4($sp)
	lw $s1 8($sp)
	lw $s2 12($sp)
	addi $sp $sp 16
	
	jr $ra
	
# a0 - packet
getWhereToStore:
	addi $sp $sp -16
	sw $s2 12($sp)
	sw $s1 8($sp)
	sw $s0 4($sp)
	sw $ra 0($sp)
	
	# s0 - packet
	# s1 - packet type
	# s2 - fragment offset
	
	move $s0 $a0 # s0 = a0
	jal getPacketType
	li $t0 -1 # t0 = -1
	beq $v0 $t0 getWhereToStore_exit # v0 == -1
	move $s1 $v0 # s1 = v0
	move $a0 $s0 # a0 = s0
	jal getFragmentOffset
	move $s2 $v0 # s2 = v0
	
	beqz $s1 getWhereToStore_start # s1 == 0
	j getWhereToStore_other
	
getWhereToStore_start:
	li $v0 0 # v0 = 0
	j getWhereToStore_exit
	
getWhereToStore_other:
	move $v0 $s2 # v0 = s2
	j getWhereToStore_exit

getWhereToStore_exit:
	lw $ra 0($sp)
	lw $s0 4($sp)
	lw $s1 8($sp)
	lw $s2 12($sp)
	addi $sp $sp 16
	jr $ra
	
# a0 - str1
# a1 - str2
# a2 - length of str1 (m)
# a3 - length of str2 (n)
editDistance:
	addi $sp $sp -32
	sw $s6 28($sp)
	sw $s5 24($sp)
	sw $s4 20($sp)
	sw $s3 16($sp)
	sw $s2 12($sp)
	sw $s1 8($sp)
	sw $s0 4($sp)
	sw $ra 0($sp)
	
	# s0 - str1
	# s1 - str2
	# s2 - m (str1 len)
	# s3 - n (str2 len)
	move $s0 $a0 # s0 = a0
	move $s1 $a1 # s1 = a1
	move $s2 $a2 # s2 = a2
	move $s3 $a3 # s3 = a3
	
	bltz $s2 editDistance_ret_neg1 # s2 < 0
	bltz $s3 editDistance_ret_neg1 # s3 < 0
	beqz $s2 editDistance_ret_n # s2 == 0
	beqz $s3 editDistance_ret_m # s3 == 0
	
	addi $t0 $s2 -1 # t0 = s2 - 1
	addi $t1 $s3 -1 # t1 = s3 - 1
	add $t2 $s0 $t0 # t2 = s0 + t0
	add $t3 $s1 $t1 # t3 = s1 + t1
	
	lb $t4 0($t2) # t2 -> t4
	lb $t5 0($t3) # t3 -> t5
	
	beq $t4 $t5 editDistance_il
	
	# s4 - insert
	# s5 - remove
	# s6 - replace
	move $a0 $s0 # a0 = s0
	move $a1 $s1 # a1 = s1
	move $a2 $s2 # a2 = s2
	addi $a3 $s3 -1 # a3 = s3 - 1
	jal editDistance
	move $s4 $v0 # s4 = v0
	
	move $a0 $s0 # a0 = s0
	move $a1 $s1 # a1 = s1
	addi $a2 $s2 -1 # a2 = s2 - 1
	move $a3 $s3 # a3 = s3
	jal editDistance
	move $s5 $v0 # s5 = v0
	
	move $a0 $s0 # a0 = s0
	move $a1 $s1 # a1 = s1
	addi $a2 $s2 -1 # a2 = s2 - 1
	addi $a3 $s3 -1 # a3 = s3 - 1
	jal editDistance
	move $s6 $v0 # s6 = v0
	
	move $a0 $s4 # a0 = s4
	move $a1 $s5 # a1 = s5
	jal getMin
	move $a0 $v0 # a0 = v0
	move $a1 $s6 # a1 = s6
	jal getMin
	addi $v0 $v0 1 # v0 = v0 + 1
	j editDistance_exit
	
editDistance_il: # il = ignore last
	move $a2 $t0 # a2 = t0
	move $a3 $t1 # a3 = t1
	jal editDistance
	j editDistance_exit
	
editDistance_ret_neg1:
	li $v0 -1 # v0 = -1
	j editDistance_exit
	
editDistance_ret_m:
	move $v0 $s2 # v0 = s2
	j editDistance_exit
	
editDistance_ret_n:
	move $v0 $s3 # v0 = s3
	j editDistance_exit
	
editDistance_exit:
	lw $ra 0($sp)
	lw $s0 4($sp)
	lw $s1 8($sp)
	lw $s2 12($sp)
	lw $s3 16($sp)
	lw $s4 20($sp)
	lw $s5 24($sp)
	lw $s6 28($sp)
	addi $sp $sp 32
	
	jr $ra
	
# a0 - fnum
# a1 - snum
getMin:
	blt $a0 $a1 getMin_ret_a0 # a0 < a1
	move $v0 $a1 # v0 = a1
	jr $ra
	
getMin_ret_a0:
	move $v0 $a0 # v0 = a0
	jr $ra

#################################################################
# Student defined data section
#################################################################
.data
.align 2  # Align next items to word boundary

#place all data declarations here

newline: .asciiz "\n"