extends Node3D

const PLOT = preload("res://Scenes/plot.tscn")
const TILE_SIZE = 1
const DIRECTIONS = [[0,1],[0,-1],[1,0],[-1,0]]
@onready var camera: Camera3D = $Camera3D
@onready var plots_node: Node3D = $Plots
var color_map = {}
var values = []
var camera_pan_speed = Vector3(0, 1.5, 2.5)
var start = false

# Called when the node enters the scene tree for the first time.
func _ready() -> void:
	var file = FileAccess.open("res://input.txt", FileAccess.READ)

	while not file.eof_reached():
		var line = file.get_line()
		if not line.is_empty():
			var p = []
			for char in line:
				p.append(char)
				if not color_map.has(char):
					var material = StandardMaterial3D.new()
					var random_color = Color(randf(), randf(), randf())
					material.albedo_color = random_color
					color_map[char] = material
			values.append(p)
	file.close()
		
	for r in range(values.size()):
		for c in range(values[r].size()):
			var p = values[r][c]
			var plot = PLOT.instantiate()
			plots_node.add_child(plot)
			plot.set_color(color_map[values[r][c]])
			plot.global_position.x = c * TILE_SIZE
			plot.global_position.z = r * TILE_SIZE
			for i in range(4):
				var d = DIRECTIONS[i]
				if needs_fence(p, c + d[1], r + d[0]):
					plot.add_fence(i)
					
	camera.transform.origin = Vector3(values.size() / 2 - 0.5, values.size() * 0.03, 3)

# Press backspace to start/pause
func _input(event):
	if event.is_action_pressed("ui_text_backspace"):
		start = !start

func needs_fence(p, c: int, r: int) -> bool:
	if r < 0 || r >= values.size() || c < 0 || c >= values[0].size():
		return true;
	return values[r][c] != p
	
func _process(delta: float) -> void:
	if start == true and camera.transform.origin.y < 90:
		var pan_speed = delta * camera_pan_speed
		camera.transform.origin += pan_speed
		camera.transform.origin.z = min(camera.transform.origin.z, values.size() * (.68))
		camera_pan_speed.z *= 1.0011
		camera_pan_speed.y *= 1.0015
