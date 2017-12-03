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
	# s6 - non-empty cell counter
	
	move $s0 $a0 # s0 = a0
	move $s1 $a1 # s1 = a1
	move $s2 $a2 # s2 = a2
	move $s3 $a3 # s3 = a3
	move $s4 $t0 # s4 = t0
	li $s6 0 # s6 = 0
	
	bltz $s3 merge_row_err # row < 0
	bge $s3 $s1 merge_row_err # row >= num rows
	li $t0 2 # t0 = 2
	blt $s1 $t0 merge_row_err # num rows < 2
	blt $s2 $t0 merge_row_err # num cols < 2
	bltz $s4 merge_row_err # direction < 0
	li $t0 1 # t0 = 1
	bgt $s4 $t0 merge_row_err # direction > 1
	
	beqz $s4 merge_row_ltr # s4 == 0 (do ltr instead)
	
	j merge_row_rtl
	
merge_row_rtl:
	addi $s5 $s2 -1 # s5 = num of cols - 1
	
	# variables for the for loop:
	# t0 - current cell addr
	# t1 - current cell value
	# t2 - previous cell value
	# t3 - the value doubled
	
merge_row_rtl_loop:
	blez $s5 merge_row_succ # col counter <= 0
	
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
	
	addi $s6 $s6 1 # s6 = s6 + 1
	
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
	bge $s5 $t8 merge_row_succ # col counter >= num cols - 1
	
	move $a0 $s0 # board argument
	move $a1 $s3 # row argument
	move $a2 $s5 # col argument
	move $a3 $s2 # number of cols argument
	jal board_calc_addr
	
	move $t0 $v0 # t0 = v0
	
	lh $t1 0($t0) # load the current cell value
	
	li $t9 -1 # t9 = -1
	beq $t1 $t9 merge_row_ltr_loop_cont # current cell value == -1
	
	addi $s6 $s6 1 # s6 = s6 + 1
	
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
	
merge_row_succ:
	move $v0 $s6 # v0 = s6
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
	lw $t0 0($sp) # load the direction argument from stack
	
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
	
	# s0 - board
	# s1 - num rows
	# s2 - num cols
	# s3 - col
	# s4 - direction
	# s5 - row counter
	# s6 - non empty cell counter
	
	move $s0 $a0 # s0 = a0
	move $s1 $a1 # s1 = a1
	move $s2 $a2 # s2 = a2
	move $s3 $a3 # s3 = a3
	move $s4 $t0 # s4 = t0
	li $s6 0 # s6 = 0
	
	bltz $s3 merge_col_err # col < 0
	bge $s3 $s2 merge_col_err # col >= num cols
	li $t0 2 # t0 = 2
	blt $s1 $t0 merge_col_err # num rows < 2
	blt $s2 $t0 merge_col_err # num cols < 2
	bltz $s4 merge_col_err # direction < 0
	li $t0 1 # t0 = 1
	bgt $s4 $t0 merge_col_err # direction > 1
	
	beqz $s4 merge_col_btt # s4 == 0
	
	j merge_col_ttb
	
merge_col_btt:
	addi $s5 $s1 -1 # s5 = num of rows - 1
	
	# variables for the for loop:
	# s7 - current cell addr
	# t1 - current cell value
	# t2 - previous cell addr
	# t3 - previous cell value
	# t4 - the value doubled
	
merge_col_btt_loop:
	blez $s5 merge_col_succ # row counter <= 0
	
	move $a0 $s0 # board argument
	move $a1 $s5 # row argument
	move $a2 $s3 # col argument
	move $a3 $s2 # number of cols argument
	jal board_calc_addr
	
	move $s7 $v0 # s7 = v0
	
	move $a0 $s0 # board argument
	addi $a1 $s5 -1 # a1 = row counter - 1 (row argument)
	move $a2 $s3 # col argument
	move $a3 $s2 # number of cols argument
	jal board_calc_addr
	
	move $t2 $v0 # t2 = v0
	
	lh $t1 0($s7)
	lh $t3 0($t2)
	
	li $t9 -1 # t9 = -1
	beq $t1 $t9 merge_col_btt_loop_cont # current cell value == -1
	
	addi $s6 $s6 1 # s6 = s6 + 1
	
	bne $t1 $t3 merge_col_btt_loop_cont # current cell value != previous cell value
	
	add $t4 $t1 $t1 # t4 = t1 * 2
	sh $t4 0($s7) # store the doubled value into the bottom cell
	sh $t9 0($t2) # store neg one in previous cell
	addi $s5 $s5 -1 # s5 = s5 - 1
	
merge_col_btt_loop_cont:
	addi $s5 $s5 -1 # s5 = s5 - 1
	
	j merge_col_btt_loop

merge_col_ttb:
	li $s5 0 # s5 = 0
	
	# variables for the for loop:
	# s7 - current cell addr
	# t1 - current cell value
	# t2 - next cell addr
	# t3 - next cell value
	# t4 - the value doubled
	
merge_col_ttb_loop:
	addi $t9 $s1 -1 # t9 = num rows - 1
	bge $s5 $t9 merge_col_succ # row counter >= num rows - 1
	
	move $a0 $s0 # board argument
	move $a1 $s5 # row argument
	move $a2 $s3 # col argument
	move $a3 $s2 # number of cols argument
	jal board_calc_addr
	
	move $s7 $v0 # s7 = v0
	
	move $a0 $s0 # board argument
	addi $a1 $s5 1 # a1 = row counter + 1 (row argument)
	move $a2 $s3 # col argument
	move $a3 $s2 # number of cols argument
	jal board_calc_addr
	
	move $t2 $v0 # t2 = v0
	
	lh $t1 0($s7)
	lh $t3 0($t2)
	
	li $t9 -1 # t9 = -1
	beq $t1 $t9 merge_col_ttb_loop_cont # current cell value == -1
	
	addi $s6 $s6 1 # s6 = s6 + 1
	
	bne $t1 $t3 merge_col_ttb_loop_cont # current cell value != previous cell value
	
	add $t4 $t1 $t1 # t4 = t1 * 2
	sh $t4 0($s7) # store the doubled value into the bottom cell
	sh $t9 0($t2) # store neg one in previous cell
	addi $s5 $s5 1 # s5 = s5 + 1
	
merge_col_ttb_loop_cont:
	addi $s5 $s5 1 # s5 = s5 + 1
	
	j merge_col_ttb_loop
	
merge_col_err:
	li $v0 -1
	j merge_col_done

merge_col_succ:
	move $v0 $s6
	j merge_col_done

merge_col_done:
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

# a0 - board
# a1 - num rows
# a2 - num cols
# a3 - row
# a4 - direction
shift_row:
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
	# s5 - current pos
	# s6 - number of cells shifted
	
	move $s0 $a0
	move $s1 $a1
	move $s2 $a2
	move $s3 $a3
	move $s4 $t0
	li $s6 0
	
	bltz $s3 shift_row_err # s3 < 0
	bge $s3 $s1 shift_row_err # row >= num rows
	li $t9 2 # t9 = 2
	blt $s1 $t9 shift_row_err # num rows < 2
	blt $s2 $t9 shift_row_err # num cols < 2
	bltz $s4 shift_row_err # direction < 0
	li $t9 1 # t9 = 1
	bgt $s4 $t9 shift_row_err # direction > 1
	
	beqz $s4 shift_row_left # direction == 0
	
	j shift_row_right
	
shift_row_left:
	# t0 - current addr
	# t1 - current value
	# t2 - previous cell value
	# t3 - neg1 flag

	li $t3 1
	li $s5 1 # s5 = 1
	
shift_row_left_loop:
	beqz $s5 shift_row_left_loop_cont # current pos == 0
	bge $s5 $s2 shift_row_succ # current pos >= num cols
	
	move $a0 $s0 # board arg
	move $a1 $s3 # row arg
	move $a2 $s5 # col arg
	move $a3 $s2 # num cols arg
	jal board_calc_addr
	
	move $t0 $v0
	lh $t1 0($t0) # load current value
	
	li $t9 -1 # t9 = -1
	beq $t1 $t9 shift_row_left_loop_n1 # current value == -1
	
	lh $t2 -2($t0) # load previous cell value
	bne $t2 $t9 shift_row_left_loop_cont # previous cell value != -1
	
	bnez $t3 shift_row_left_loop_ic # t3 != 0

shift_row_left_loop_icc: # icc = increment counter cont
	
	sh $t1 -2($t0) # move current value to the left by one
	sh $t9 0($t0) # put -1 at current position
	
	addi $s5 $s5 -1 # s5 = s5 - 1
	j shift_row_left_loop
	
shift_row_left_loop_ic: # ic = increment counter
	addi $s6 $s6 1
	li $t3 0 # t3 = 0
	j shift_row_left_loop_icc
	
shift_row_left_loop_n1: # n1 = negative 1
	li $t3 1
	j shift_row_left_loop_cont
	
shift_row_left_loop_cont:
	addi $s5 $s5 1 # s5 = s5 + 1
	j shift_row_left_loop

shift_row_right:
	# t0 - current addr
	# t1 - current value
	# t2 - next cell value
	# t3 - neg1 flag

	li $t3 1
	addi $s5 $s2 -2 # s5 = num cols - 2
	
shift_row_right_loop:
	addi $t9 $s2 -1 # t9 = num cols - 1
	beq $s5 $t9 shift_row_right_loop_cont # current pos == num cols - 1
	bltz $s5 shift_row_succ # current pos < 0
	
	move $a0 $s0 # board arg
	move $a1 $s3 # row arg
	move $a2 $s5 # col arg
	move $a3 $s2 # num cols arg
	jal board_calc_addr
	
	move $t0 $v0
	lh $t1 0($t0) # load current value
	
	li $t9 -1 # t9 = -1
	beq $t1 $t9 shift_row_right_loop_n1 # current value == -1
	
	lh $t2 2($t0) # load next cell value
	bne $t2 $t9 shift_row_right_loop_cont # next cell value != -1
	
	bnez $t3 shift_row_right_loop_ic # t3 != 0

shift_row_right_loop_icc: # icc = increment counter cont
	
	sh $t1 2($t0) # move current value to the right by one
	sh $t9 0($t0) # put -1 at current position
	
	addi $s5 $s5 1 # s5 = s5 + 1
	j shift_row_right_loop
	
shift_row_right_loop_ic: # ic = increment counter
	addi $s6 $s6 1
	li $t3 0 # t3 = 0
	j shift_row_right_loop_icc
	
shift_row_right_loop_n1: # n1 = negative 1
	li $t3 1
	j shift_row_right_loop_cont
	
shift_row_right_loop_cont:
	addi $s5 $s5 -1 # s5 = s5 - 1
	j shift_row_right_loop
	
shift_row_err:
	li $v0 -1 # v0 = -1
	j shift_row_done
	
shift_row_succ:
	move $v0 $s6 # v0 = s6
	j shift_row_done
	
shift_row_done:
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
shift_col:
	lw $t0 0($sp) # load direction argument

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
	
	# s0 - board
	# s1 - num rows
	# s2 - num cols
	# s3 - col
	# s4 - direction
	# s5 - current pos
	# s6 - number of cells shifted
	
	move $s0 $a0
	move $s1 $a1
	move $s2 $a2
	move $s3 $a3
	move $s4 $t0
	li $s6 0
	
	bltz $s3 shift_col_err # col < 0
	bge $s3 $s2 shift_col_err # col >= num cols
	li $t9 2 # t9 = 2
	blt $s1 $t9 shift_col_err # num rows < 2
	blt $s2 $t9 shift_col_err # num cols < 2
	bltz $s4 shift_col_err # direction < 0
	li $t9 1 # t9 = 1
	bgt $s4 $t9 shift_col_err # direction > 1
	
	beqz $s4 shift_col_up # s4 == 0
	
	j shift_col_down
	
shift_col_up:
	# s7 - current addr
	# t1 - current value
	# t2 - previous addr
	# t3 - previous cell value
	# t4 - neg1 flag

	li $t4 1
	li $s5 1 # s5 = 1
	
shift_col_up_loop:
	beqz $s5 shift_col_up_loop_cont # current pos == 0
	bge $s5 $s1 shift_col_succ # current pos >= num rows
	
	move $a0 $s0 # board arg
	move $a1 $s5 # row arg
	move $a2 $s3 # col arg
	move $a3 $s2 # num cols arg
	jal board_calc_addr
	
	move $s7 $v0 # s7 = v0
	
	move $a0 $s0 # board arg
	addi $a1 $s5 -1 # a1 = current pos - 1 (row arg)
	move $a2 $s3 # col arg
	move $a3 $s2 # num cols arg
	jal board_calc_addr
	
	move $t2 $v0 # t2 = v0
	
	lh $t1 0($s7) # load current value
	lh $t3 0($t2) # load previous value
	
	li $t9 -1 # t9 = -1
	beq $t1 $t9 shift_col_up_loop_n1 # current value == -1
	bne $t3 $t9 shift_col_up_loop_cont # previous value != -1
	bnez $t4 shift_col_up_loop_ic # flag != 0
	
shift_col_up_loop_icc: # icc = increment counter continue
	sh $t1 0($t2) # store current value into previous addr
	sh $t9 0($s7) # store -1 into current addr
	addi $s5 $s5 -1 # s5 = s5 - 1
	j shift_col_up_loop
	
shift_col_up_loop_ic: # ic = increment counter
	addi $s6 $s6 1 # s6 = s6 + 1
	li $t4 0 # t4 = 0
	j shift_col_up_loop_icc
	
shift_col_up_loop_n1:
	li $t4 1 # t4 = 1
	j shift_col_up_loop_cont
	
shift_col_up_loop_cont:
	addi $s5 $s5 1 # s5 = s5 + 1
	j shift_col_up_loop
	
shift_col_down:
	# s7 - current addr
	# t1 - current value
	# t2 - next addr
	# t3 - next cell value
	# t4 - neg1 flag

	li $t4 1
	addi $s5 $s1 -2 # s5 = num rows - 2
	
shift_col_down_loop:
	addi $t9 $s1 -1 # t9 = num rows - 1
	beq $s5 $t9 shift_col_down_loop_cont # current pos == num rows - 1
	bltz $s5 shift_col_succ # current pos < 0
	
	move $a0 $s0 # board arg
	move $a1 $s5 # row arg
	move $a2 $s3 # col arg
	move $a3 $s2 # num cols arg
	jal board_calc_addr
	
	move $s7 $v0 # s7 = v0
	
	move $a0 $s0 # board arg
	addi $a1 $s5 1 # a1 = current pos + 1 (row arg)
	move $a2 $s3 # col arg
	move $a3 $s2 # num cols arg
	jal board_calc_addr
	
	move $t2 $v0 # t2 = v0
	
	lh $t1 0($s7) # load current value
	lh $t3 0($t2) # load next value
	
	li $t9 -1 # t9 = -1
	beq $t1 $t9 shift_col_down_loop_n1 # current value == -1
	bne $t3 $t9 shift_col_down_loop_cont # next value != -1
	bnez $t4 shift_col_down_loop_ic # flag != 0
	
shift_col_down_loop_icc: # icc = increment counter continue
	sh $t1 0($t2) # store current value into previous addr
	sh $t9 0($s7) # store -1 into current addr
	addi $s5 $s5 1 # s5 = s5 + 1
	j shift_col_down_loop
	
shift_col_down_loop_ic: # ic = increment counter
	addi $s6 $s6 1 # s6 = s6 + 1
	li $t4 0 # t4 = 0
	j shift_col_down_loop_icc
	
shift_col_down_loop_n1:
	li $t4 1 # t4 = 1
	j shift_col_down_loop_cont
	
shift_col_down_loop_cont:
	addi $s5 $s5 -1 # s5 = s5 - 1
	j shift_col_down_loop
	
shift_col_err:
	li $v0 -1
	j shift_col_done
	
shift_col_succ:
	move $v0 $s6
	j shift_col_done

shift_col_done:
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

# a0 - board
# a1 - num rows
# a2 - num cols
check_state:
	addi $sp $sp -28
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
	
	move $s0 $a0
	move $s1 $a1
	move $s2 $a2
	
	# t0 - 2048
	# t1 - counter
	# t2 - num cells
	# t3 - current addr
	# t4 - current value
	
	li $t0 2048 # t0 = 2048
	li $t1 1 # t1 = 1
	mul $t2 $s1 $s2 # t2 = s1 * s2
	move $t3 $s0 # t3 = (0,0)
	
check_state_cw_loop: # cw = check win
	bgt $t1 $t2 check_state_cw_done
	
	lh $t4 0($t3)
	bge $t4 $t0 check_state_won # current value >= 2048
	addi $t3 $t3 2 # t3 = t3 + 2
	addi $t1 $t1 1 # t1 = t1 + 1
	
	j check_state_cw_loop
	
check_state_cw_done:
	# s3 - row counter
	# s4 - col counter
	# s5 - current addr
	# t0 - next addr
	# t1 - current value
	# t2 - next value
	
	li $s3 0 # s3 = 0
	li $s4 0 # s4 = 0
	
check_state_cl_loop: # cl = check loss
	bge $s3 $s1 check_state_cl_loop2_init # row counter >= num rows
	addi $t9 $s2 -1 # t9 = num cols - 1
	bge $s4 $t9 check_state_cl_loop_ir # col counter >= num cols - 1
	
	move $a0 $s0 # board arg
	move $a1 $s3 # row arg
	move $a2 $s4 # col arg
	move $a3 $s2 # num cols arg
	jal board_calc_addr
	
	move $s5 $v0
	
	move $a0 $s0 # board arg
	move $a1 $s3 # row arg
	addi $a2 $s4 1 # a2 = col counter + 1 (col arg)
	move $a3 $s2 # num cols arg
	jal board_calc_addr
	
	move $t0 $v0 # t0 = v0
	
	lh $t1 0($s5) # load current value
	lh $t2 0($t0) # load next value
	
	li $t9 -1 # t9 = -1
	beq $t1 $t9 check_state_neither
	beq $t2 $t9 check_state_neither
	beq $t1 $t2 check_state_neither
	
	addi $s4 $s4 1 # s4 = s4 + 1
	
	j check_state_cl_loop
	
check_state_cl_loop_ir: # ir = increment row
	addi $s3 $s3 1 # s3 = s3 + 1
	li $s4 0 # col counter = 0
	j check_state_cl_loop
	
check_state_cl_loop2_init:
	# s3 - row counter
	# s4 - col counter
	# s5 - current addr
	# t0 - next addr
	# t1 - current value
	# t2 - next value
	
	li $s3 0 # s3 = 0
	li $s4 0 # s4 = 0
	
	j check_state_cl_loop2
	
check_state_cl_loop2: # cl = check loss
	addi $t9 $s1 -1 # t9 = num rows - 1
	bge $s3 $t9 check_state_lost # row counter >= num rows - 1
	bge $s4 $s2 check_state_cl_loop2_ir # col counter >= num cols
	
	move $a0 $s0 # board arg
	move $a1 $s3 # row arg
	move $a2 $s4 # col arg
	move $a3 $s2 # num cols arg
	jal board_calc_addr
	
	move $s5 $v0
	
	move $a0 $s0 # board arg
	addi $a1 $s3 1 # a1 = row counter + 1 (row arg)
	move $a2 $s4 # col arg
	move $a3 $s2 # num cols arg
	jal board_calc_addr
	
	move $t0 $v0 # t0 = v0
	
	lh $t1 0($s5) # load current value
	lh $t2 0($t0) # load next value
	
	li $t9 -1 # t9 = -1
	beq $t1 $t9 check_state_neither
	beq $t2 $t9 check_state_neither
	beq $t1 $t2 check_state_neither
	
	addi $s4 $s4 1 # s4 = s4 + 1
	
	j check_state_cl_loop2
	
check_state_cl_loop2_ir: # ir = increment row
	addi $s3 $s3 1 # s3 = s3 + 1
	li $s4 0 # col counter = 0
	j check_state_cl_loop2
	
check_state_won:
	li $v0 1 # v0 = 1
	j check_state_done
	
check_state_lost:
	li $v0 -1 # v0 = -1
	j check_state_done
	
check_state_neither:
	li $v0 0 # v0 = 0
	j check_state_done
	
check_state_done:
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
# a1 - num rows
# a2 - num cols
# a3 - direction
user_move:
	addi $sp $sp -28
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
	# s3 - direction
	# s4 - row/col counter
	# s5 - direction arg for functions
	
	move $s0 $a0
	move $s1 $a1
	move $s2 $a2
	move $s3 $a3
	li $s4 0 # s4 = 0
	
	li $t0 'L'
	beq $s3 $t0 user_move_left
	li $t0 'R'
	beq $s3 $t0 user_move_right
	li $t0 'U'
	beq $s3 $t0 user_move_up
	li $t0 'D'
	beq $s3 $t0 user_move_down

	j user_move_err
	
user_move_left: # dv = direction valid
	li $s5 0 # s5 = 1
	j user_move_row
	
user_move_right: # dv = direction valid
	li $s5 1 # s5 = 0
	j user_move_row
	
user_move_up: # dv = direction valid
	li $s5 0 # s5 = 0
	j user_move_col
	
user_move_down: # dv = direction valid
	li $s5 1 # s5 = 1
	j user_move_col
	
user_move_row:
	bge $s4 $s1 user_move_cs # row counter >= num rows
	
	move $a0 $s0 # board arg
	move $a1 $s1 # num rows arg
	move $a2 $s2 # num cols arg
	move $a3 $s4 # row arg
	addi $sp $sp -4
	sw $s5 0($sp) # direction arg
	jal shift_row
	addi $sp $sp 4
	bltz $v0 user_move_err # v0 < 0
	
	move $a0 $s0 # board arg
	move $a1 $s1 # num rows arg
	move $a2 $s2 # num cols arg
	move $a3 $s4 # row arg
	addi $sp $sp -4
	sw $s5 0($sp) # direction arg
	jal merge_row
	addi $sp $sp 4
	bltz $v0 user_move_err # v0 < 0
	
	move $a0 $s0 # board arg
	move $a1 $s1 # num rows arg
	move $a2 $s2 # num cols arg
	move $a3 $s4 # row arg
	addi $sp $sp -4
	sw $s5 0($sp) # direction arg
	jal shift_row
	addi $sp $sp 4
	bltz $v0 user_move_err # v0 < 0
	
	addi $s4 $s4 1 # s4 = s4 + 1
	j user_move_row

user_move_col:
	bge $s4 $s2 user_move_cs # col counter >= num cols
	
	move $a0 $s0 # board arg
	move $a1 $s1 # num rows arg
	move $a2 $s2 # num cols arg
	move $a3 $s4 # col arg
	addi $sp $sp -4
	sw $s5 0($sp) # direction arg
	jal shift_col
	addi $sp $sp 4
	bltz $v0 user_move_err # v0 < 0
	
	move $a0 $s0 # board arg
	move $a1 $s1 # num rows arg
	move $a2 $s2 # num cols arg
	move $a3 $s4 # col arg
	addi $sp $sp -4
	sw $s5 0($sp) # direction arg
	jal merge_col
	addi $sp $sp 4
	bltz $v0 user_move_err # v0 < 0
	
	move $a0 $s0 # board arg
	move $a1 $s1 # num rows arg
	move $a2 $s2 # num cols arg
	move $a3 $s4 # col arg
	addi $sp $sp -4
	sw $s5 0($sp) # direction arg
	jal shift_col
	addi $sp $sp 4
	bltz $v0 user_move_err # v0 < 0
	
	addi $s4 $s4 1 # s4 = s4 + 1
	j user_move_col

user_move_cs: # cs = check state
	move $a0 $s0 # board argument
	move $a1 $s1 # num rows arg
	move $a2 $s2 # num cols arg
	jal check_state
	move $v1 $v0
	li $v0 0
	j user_move_done
	
user_move_err:
	li $v0 -1 # v0 = -1
	li $v1 -1 # v1 = -1
	j user_move_done
	
user_move_done:
	lw $ra 0($sp)
	lw $s0 4($sp)
	lw $s1 8($sp)
	lw $s2 12($sp)
	lw $s3 16($sp)
	lw $s4 20($sp)
	lw $s5 24($sp)
	addi $sp $sp 28

	jr $ra

#################################################################
# Student defined data section
#################################################################
.data
.align 2  # Align next items to word boundary

#place all data declarations here


