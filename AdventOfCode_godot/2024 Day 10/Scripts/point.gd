extends ColorRect

@onready var point_node: ColorRect = $"."
@onready var label_node: Label = $Label
	
func set_value(val: int):
	label_node.text = str(val)
	var c = (20 + (val / 9.0) * 235) / 255.0
	point_node.color = Color(c, c, c, 0.8)
	set_process(false)
	set_physics_process(false)
