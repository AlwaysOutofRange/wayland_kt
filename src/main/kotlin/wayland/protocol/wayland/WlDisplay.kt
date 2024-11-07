package wayland.protocol.wayland

import wayland.MessageBuilder
import wayland.MessageHeader
import wayland.Wayland
import wayland.protocol.Event
import wayland.protocol.WaylandObject

class WlDisplay internal constructor(
    private val wayland: Wayland,
    override val objectId: Int = 1
) : WaylandObject {
    @Event(opcode = 0)
    fun error(objectId: Int, code: Int, message: String) {
        println("[ERROR] ObjectId: $objectId - ErrorCode: $code - Message: $message")
    }

    fun getRegistry(): WlRegistry {
        val registryId = ++wayland.nextId

        val msg = MessageBuilder(
            MessageHeader(
                objectId = this.objectId,
                opcode = 1 // wl_display@get_registry
            )
        ).putInt(registryId).build()

        wayland.send(msg)

        return WlRegistry(wayland, registryId)
    }
}