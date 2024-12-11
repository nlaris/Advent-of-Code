extends Node2D

const COLOR_FADE_RATE = .99
const COLOR_FADE_SCALE = .6

@onready var line: Line2D = $Line2D
@onready var scene: Node2D = get_tree().root.get_child(0)

var trigger_consolidate
var dampen_color
var base_color

func init(points, color, dampen_color, trigger_consolidate) -> void:
	line.points = points
	line.default_color = color
	base_color = color
	self.trigger_consolidate = trigger_consolidate
	self.dampen_color = dampen_color

func _process(delta: float) -> void:
	if dampen_color == true:
		var min_r = base_color.r * COLOR_FADE_SCALE
		var min_g = base_color.g * COLOR_FADE_SCALE
		var min_b = base_color.b * COLOR_FADE_SCALE
		line.default_color.r = max(line.default_color.r * COLOR_FADE_RATE, min_r)
		line.default_color.g = max(line.default_color.g * COLOR_FADE_RATE, min_g)
		line.default_color.b = max(line.default_color.b * COLOR_FADE_RATE, min_b)
		if trigger_consolidate == true and abs(line.default_color.r - min_r) < 0.001 and abs(line.default_color.g - min_g) < 0.001 and abs(line.default_color.b - min_b) < 0.001:
			scene.consolidate_lines()
			trigger_consolidate = false

func get_points():
	return line.points
	
func get_color():
	return line.default_color
