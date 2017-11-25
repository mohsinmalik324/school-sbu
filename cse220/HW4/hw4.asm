##############################################################
# Homework #4
# name: Mohsin Malik
# sbuid: 110880864
##############################################################

.text

##################
# MY OWN FUNCTIONS
##################

# a0 - 2d array
# a1 - i
# a2 - j
# a3 - number of columns
board_calc_addr:
	li $t0 2 # t0 = 2
	mul $t1 $a3 $t0 # t1 = n_cols * sizeof
	mul $t1 $t1 $a1 # t1 = row_size * i
	mul $t2 $a2 $t0 # t2 = j * sizeof
	add $t1 $t1 $t2 # t1 = (row_size * i) + (j * sizeof)
	add $v0 $a0 $t1 # v0 = base_addr + (row_size * i) + (sizeof * j)
	jr $ra

##############################
# PART 1 FUNCTIONS
##############################

# a0 - board
# a1 - number of rows
# a2 - number of columns
clear_board:
	addi $sp $sp -24
	sw $s4 20($sp)
	sw $s3 16($sp)
	sw $s2 12($sp)
	sw $s1 8($sp)
	sw $s0 4($sp)
	sw $ra 0($sp)

	li $t0 2 # t0 = 2
	blt $a1 $t0 clear_board_err # a1 < 2
	blt $a2 $t0 clear_board_err # a2 < 2
	
	# s0 - board
	# s1 - n_rows
	# s2 - n_cols
	# s3 - i counter
	# s4 - j counter
	
	move $s0 $a0 # s0 = a0
	move $s1 $a1 # s1 = a1
	move $s2 $a2 # s2 = a2
	
	li $s3 0 # s0 = 0
	
clear_board_i_loop:
	bge $s3 $s1 clear_board_i_loop_done # s3 >= s1
	li $s4 0 # s4 = 0
	
clear_board_j_loop:
	bge $s4 $s2 clear_board_j_loop_done # s4 >= s2
	
	move $a0 $s0 # a0 = s0
	move $a1 $s3 # a1 = s3
	move $a2 $s4 # a2 = s4
	move $a3 $s2 # a3 = s2
	jal board_calc_addr
	li $t0 -1 # t0 = -1
	sh $t0 0($v0) # t0 -> v0
	
	addi $s4 $s4 1 # s4 = s4 + 1
	j clear_board_j_loop
	
clear_board_j_loop_done:
	addi $s3 $s3 1 # s3 = s3 + 1
	j clear_board_i_loop
	
clear_board_i_loop_done:

	li $v0 0
	j clear_board_done
	
clear_board_err:
	li $v0 -1 # v0 = -1
	j clear_board_done
	
clear_board_done:
	lw $ra 0($sp)
	lw $s0 4($sp)
	lw $s1 8($sp)
	lw $s2 12($sp)
	lw $s3 16($sp)
	lw $s4 20($sp)
	addi $sp $sp 24

	jr $ra

# a0 - board
# a1 - number of rows
# a2 - number of cols
# a3 - row
# a4 - col
# a5 - value
place:
	lw $t0 0($sp) # col
	lw $t1 4($sp) # val

	addi $sp $sp -28
	sw $s5 24($sp)
	sw $s4 20($sp)
	sw $s3 16($sp)
	sw $s2 12($sp)
	sw $s1 8($sp)
	sw $s0 4($sp)
	sw $ra 0($sp)
	
	# s0 - board
	# s1 - number of rows
	# s2 - number of cols
	# s3 - row
	# s4 - col
	# s5 - value
	
	move $s0 $a0 # s0 = a0
	move $s1 $a1 # s1 = a1
	move $s2 $a2 # s2 = a2
	move $s3 $a3 # s3 = a3
	move $s4 $t0 # s4 = t0
	move $s5 $t1 # s5 = t1
	
	li $t0 2 # t0 = 2
	
	blt $s1 $t0 place_err # number of rows < 2
	blt $s2 $t0 place_err # number of cols < 2
	bltz $s3 place_err # row < 0
	bge $s3 $s1 place_err # row >= number of rows
	bltz $s4 place_err # col < 0
	bge $s4 $s2 place_err # row >= number of cols
	li $t0 -1 # t0 = -1
	bne $s5 $t0 place_check_value # value != -1
	
place_check_value_success:
	move $a0 $s0 # a0 = s0
	move $a1 $s3 # a1 = s3
	move $a2 $s4 # a2 = s4
	move $a3 $s2 # a3 = s2
	
	jal board_calc_addr
	
	sh $s5 0($v0)
	
	li $v0 0 # v0 = 0
	j place_done
	
place_check_value:
	li $t0 2 # t0 = 2
	blt $s5 $t0 place_err # value < 2
	addi $t0 $s5 -1 # t0 = value - 1
	and $t0 $t0 $s5 # t0 = (value - 1) AND value
	bnez $t0 place_err # t0 != 0
	j place_check_value_success
	
place_err:
	li $v0 -1 # v0 = -1
	j place_done

place_done:
	lw $ra 0($sp)
	lw $s0 4($sp)
	lw $s1 8($sp)
	lw $s2 12($sp)
	lw $s3 16($sp)
	lw $s4 20($sp)
	lw $s5 24($sp)
	addi $sp $sp 28

	jr $ra

# a0 - board
# a1 - number of rows
# a2 - number of columns
# a3 - r1
# a4 - c1
# a5 - r2
# a6 - c2
start_game:
	lw $t0 0($sp) # c1
	lw $t1 4($sp) # r2
	lw $t2 8($sp) # c2
	
	addi $sp $sp -32
	sw $s6 28($sp)
	sw $s5 24($sp)
	sw $s4 20($sp)
	sw $s3 16($sp)
	sw $s2 12($sp)
	sw $s1 8($sp)
	sw $s0 4($sp)
	sw $ra 0($sp)
	
	# s0 - board
	# s1 - number of rows
	# s2 - number of cols
	# s3 - r1
	# s4 - c1
	# s5 - r2
	# s6 - c2
	
	move $s0 $a0 # s0 = a0
	move $s1 $a1 # s1 = a1
	move $s2 $a2 # s2 = a2
	move $s3 $a3 # s3 = a3
	move $s4 $t0 # s4 = t0
	move $s5 $t1 # s5 = t1
	move $s6 $t2 # s6 = t2
	
	li $t0 2 # t0 = 2
	
	blt $s1 $t0 start_game_err # number of rows < 2
	blt $s2 $t0 start_game_err # number of cols < 2
	bltz $s3 start_game_err # r1 < 0
	bltz $s4 start_game_err # c1 < 0
	bltz $s5 start_game_err # r2 < 0
	bltz $s6 start_game_err # c2 < 0
	bge $s3 $s1 start_game_err # r1 >= number of rows
	bge $s5 $s1 start_game_err # r2 >= number of rows
	bge $s4 $s2 start_game_err # c1 >= number of cols
	bge $s6 $s2 start_game_err # c2 >= number of cols
	
	move $a0 $s0 # a0 = board
	move $a1 $s1 # a1 = number of rows
	move $a2 $s2 # a2 = number of cols
	jal clear_board
	
	move $a0 $s0 # a0 = board
	move $a1 $s1 # a1 = number of rows
	move $a2 $s2 # a2 = number of cols
	move $a3 $s3 # a3 = r1
	
	li $t0 2 # t0 = 2
	
	addi $sp $sp -8
	sw $t0 4($sp) # store value
	sw $s4 0($sp) # store c1
	
	jal place
	addi $sp $sp 8
	
	move $a0 $s0 # a0 = board
	move $a1 $s1 # a1 = number of rows
	move $a2 $s2 # a2 = number of cols
	move $a3 $s5 # a3 = r2
	
	li $t0 2 # t0 = 2
	
	addi $sp $sp -8
	sw $t0 4($sp) # store value
	sw $s6 0($sp) # store c2
	
	jal place
	addi $sp $sp 8
	
	j start_game_end
	
start_game_err:
	li $v0 -1 # v0 = -1
	j start_game_end
	
start_game_end:
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

##############################
# PART 2 FUNCTIONS
##############################

# a0 - board
# a1 - num rows
# a2 - num cols
# a3 - row
# a4 - direction
merge_row:
	lw $t0 0($sp) # load direction argument
	
	addi $sp $sp -32
	sw $s6 28($sp)
	sw $s5 24($sp)
	sw $s4 20($sp)
	sw $s3 16($sp)
	sw $s2 12($sp)
	sw $s1 8($sp)
	sw $s0 4($sp)
	sw $ra 0($sp)
	
	# s0 - board
	# s1 - num rows
	# s2 - num cols
	# s3 - row
	# s4 - direction
	# s5 - col counter
	# s6 - empty cell counter
	
	move $s0 $a0 # s0 = a0
	move $s1 $a1 # s1 = a1
	move $s2 $a2 # s2 = a2
	move $s3 $a3 # s3 = a3
	move $s4 $t0 # s4 = t0
	
	bltz $s3 merge_row_err # row < 0
	bge $s3 $s1 merge_row_err # row >= num rows
	li $t0 2 # t0 = 2
	blt $s1 $t0 merge_row_err # num rows < 2
	blt $s2 $t0 merge_row_err # num cols < 2
	bltz $s4 merge_row_err # direction < 0
	li $t0 1 # t0 = 1
	bgt $s4 $t0 merge_row_err # direction > 1
	
	beqz $s4 merge_row_ltr # s4 == 0
	
merge_row_rtl:
	
	addi $s5 $s2 -1 # s5 = num of cols - 1
	
	# variables for the for loop:
	# t0 - current cell addr
	# t1 - current cell value
	# t2 - previous cell value
	# t3 - the value doubled
	
merge_row_rtl_loop:
	blez $s5 merge_row_done # col counter <= 0
	
	move $a0 $s0 # board argument
	move $a1 $s3 # row argument
	move $a2 $s5 # col argument
	move $a3 $s2 # number of cols argument
	jal board_calc_addr
	
	move $t0 $v0 # t0 = v0
	
	lh $t1 0($t0) # load the current cell value
	
	li $t9 -1 # t9 = -1
	beq $t1 $t9 merge_row_rtl_loop_cont # current cell value == -1
	
	lh $t2 -2($t0) # load the previous cell value
	
	bne $t1 $t2 merge_row_rtl_loop_cont # t1 != t2
	
	add $t3 $t1 $t1 # t3 = t1 * 2
	sh $t3 0($t0)
	sh $t9 -2($t0)
	
	addi $s5 $s5 -1 # col_counter
	
merge_row_rtl_loop_cont:
	addi $s5 $s5 -1 # col_counter
	
	j merge_row_rtl_loop

merge_row_ltr:
	
	li $s5 0 # col counter = 0
	
	# variables for the for loop:
	# t0 - current cell addr
	# t1 - current cell value
	# t2 - next cell value
	# t3 - the value doubled
	# t8 - s2 minus 1
	
	addi $t8 $s2 -1 # t8 = num cols - 1
	
merge_row_ltr_loop:
	bge $s5 $t8 merge_row_done # col counter >= num cols - 1
	
	move $a0 $s0 # board argument
	move $a1 $s3 # row argument
	move $a2 $s5 # col argument
	move $a3 $s2 # number of cols argument
	jal board_calc_addr
	
	move $t0 $v0 # t0 = v0
	
	lh $t1 0($t0) # load the current cell value
	
	li $t9 -1 # t9 = -1
	beq $t1 $t9 merge_row_ltr_loop_cont # current cell value == -1
	
	lh $t2 2($t0) # load the next cell value
	
	bne $t1 $t2 merge_row_ltr_loop_cont # t1 != t2
	
	add $t3 $t1 $t1 # t3 = t1 * 2
	sh $t3 0($t0)
	sh $t9 2($t0)
	
	addi $s5 $s5 1 # col_counter++
	
merge_row_ltr_loop_cont:
	addi $s5 $s5 1 # col_counter++
	
	j merge_row_ltr_loop
	
merge_row_err:
	li $v0 -1 # v0 = -1
	j merge_row_done
	
merge_row_done:
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

# a0 - board
# a1 - num rows
# a2 - num cols
# a3 - col
# a4 - direction
merge_col:
	jr $ra

shift_row:
    #Define your code here
    ############################################
    # DELETE THIS CODE. Only here to allow main program to run without fully implementing the function
    li $s0, 0x777
    li $v0, 0x777
    ############################################
    jr $ra

shift_col:
    #Define your code here
    ############################################
    # DELETE THIS CODE. Only here to allow main program to run without fully implementing the function
    li $s0, 0x777
    li $v0, 0x777
    ############################################
    jr $ra

check_state:
    #Define your code here
    ############################################
    # DELETE THIS CODE. Only here to allow main program to run without fully implementing the function
    li $s0, 0x777
    li $v0, 0x777
    ############################################
    jr $ra

user_move:
    #Define your code here
    ############################################
    # DELETE THIS CODE. Only here to allow main program to run without fully implementing the function
    li $s0, 0x777
    li $v0, 0x777
    li $v1, 0x777
    ############################################
    jr $ra

#################################################################
# Student defined data section
#################################################################
.data
.align 2  # Align next items to word boundary

#place all data declarations here


