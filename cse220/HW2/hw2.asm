
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

printStringArray:
    #Define your code here
	############################################
	# DELETE THIS CODE. Only here to allow main program to run without fully implementing the function
	li $v0, -555
	############################################
    jr $ra

verifyIPv4Checksum:
    #Define your code here
	############################################
	# DELETE THIS CODE. Only here to allow main program to run without fully implementing the function
	li $v0, -555
	############################################
    jr $ra

##############################
# PART 2 FUNCTIONS
##############################

extractData:
    #Define your code here
	############################################
	# DELETE THIS CODE. Only here to allow main program to run without fully implementing the function
	li $v0, -555
	li $v1, -555
	############################################
    jr $ra

processDatagram:
    #Define your code here

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


