extends Node2D

const LINE = preload("res://Scenes/line.tscn")
const POINT = preload("res://Scenes/point.tscn")
const COLORS = [Color.YELLOW, Color.RED, Color.BLUE_VIOLET, Color.GREEN, Color.AQUA, Color.DARK_ORANGE, Color.BLUE]
const TILE_SIZE = 32
const DIRECTIONS = [[0,1],[0,-1],[1,0],[-1,0]]
const SPEED_DECAY = .975
const MIN_SPEED = .005

@onready var points_node: Node2D = $Points
@onready var complete_lines_node: Node2D = $CompleteLines
@onready var lines_node: Node2D = $Lines
@onready var label_node: Label = $CanvasLayer/Label

var timer = 0
var step_rate = .35
var path_index = 0
var step_index = 0
var color_index = 0
var complete_lines = []
var current_line = []
var paths = []
var values = []
var start = false

func _ready() -> void:
	var file = FileAccess.open("res://input.txt", FileAccess.READ)
	var x = 0
	var y = 0

	while not file.eof_reached():
		var line = file.get_line()
		if not line.is_empty():
			var nums = []
			for char in line:
				nums.append(int(char))
				var point = POINT.instantiate()
				points_node.add_child(point)
				point.set_value(int(char))
				point.global_position.x = x * TILE_SIZE
				point.global_position.y = y * TILE_SIZE
				x += 1
			values.append(nums)
		y += 1
		x = 0

	for r in range(values.size()):
		for c in range(values[r].size()):
			get_trails([], 0, r, c)

	label_node.text = "0"
	file.close()
	
func _process(delta: float) -> void:
	if start == true and path_index < paths.size():
		if timer > step_rate:
			timer = 0
			step_rate = max(step_rate * SPEED_DECAY, MIN_SPEED)
			var path = paths[path_index]
			var start_point = Vector2(path[step_index][1] * TILE_SIZE + (TILE_SIZE / 2), path[step_index][0] * TILE_SIZE + (TILE_SIZE / 2))
			var end_point = Vector2(path[step_index + 1][1] * TILE_SIZE + (TILE_SIZE / 2), path[step_index + 1][0] * TILE_SIZE + (TILE_SIZE / 2))
			var line = LINE.instantiate()
			lines_node.add_child(line)
			current_line.append(line)
			line.init([start_point, end_point], COLORS[color_index], true, step_index == 8)
			if step_index == 8:
				complete_lines.append(current_line)
				current_line = []
				path_index += 1
				step_index = 0
				label_node.text = str(path_index)
				if path_index < paths.size() and paths[path_index][0] != path[0]:
					color_index = (color_index + 1) % COLORS.size()
			else:
				step_index += 1
		timer += delta

# Press backspace to start/pause
func _input(event):
	if event.is_action_pressed("ui_text_backspace"):
		start = !start

func consolidate_lines() -> void:
	var points = []
	var line = complete_lines[0]
	var color = line[0].get_color()
	complete_lines = complete_lines.slice(1)
	for l in line:
		points.append(l.get_points()[0])
		if points.size() == 9:
			points.append(l.get_points()[1])
		l.queue_free()
	var complete_line = LINE.instantiate()
	complete_lines_node.add_child(complete_line)
	complete_line.init(points, color, false, false)

func get_trails(arr: Array, target: int, r: int, c: int):
	if values[r][c] != target:
		return
	arr.append([r, c])
	if values[r][c] == 9:
		paths.append(arr)
		return
	for i in range(4):
		var d = DIRECTIONS[i]
		if get_point(r + d[0], c + d[1]) == target + 1:
			get_trails(arr.duplicate(), target + 1, r + d[0], c + d[1])

func get_point(r: int, c: int) -> int:
	if r < 0 or r >= values.size() or c < 0 or c >= values[r].size():
		return -1
	return values[r][c]
