extends Node3D

@onready var flowers_1: MeshInstance3D = $Flowers1
@onready var flowers_2: MeshInstance3D = $Flowers2
@onready var fence: Node3D = $Fence
@onready var fence_2: Node3D = $Fence2
@onready var fence_3: Node3D = $Fence3
@onready var fence_4: Node3D = $Fence4

func set_color(material: StandardMaterial3D) -> void:
	flowers_1.set_surface_override_material(0, material)
	flowers_2.set_surface_override_material(0, material)
	
func add_fence(side: int) -> void:
	if side == 0:
		fence.visible = true
	elif side == 1:
		fence_2.visible = true
	elif side == 2:
		fence_3.visible = true
	else:
		fence_4.visible = true
