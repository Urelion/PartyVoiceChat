package world.urelion.partyvoicechat;

import de.maxhenkel.voicechat.api.events.MicrophonePacketEvent;
import lombok.extern.slf4j.Slf4j;
import me.pikamug.unite.api.objects.PartyProvider;
import org.bukkit.Server;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.Nullable;

/**
 * send Voice Chat packets to all party members of the sender
 *
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
public class VoiceChatListener
implements Listener {
	/**
	 * called whenever a player sends audio to the {@link Server}
	 * via the voice chat
	 *
	 * @param event the {@link MicrophonePacketEvent} to be handled
	 *
	 * @since 1.0.0
	 */
	public void onMicrophone(MicrophonePacketEvent event) {
		VoiceChatListener.log.trace("Get Party Voice Chat plugin");
		final @Nullable PartyVoiceChatPlugin partyVoiceChatPlugin =
			PartyVoiceChatPlugin.getINSTANCE();
		VoiceChatListener.log.trace(
			"Check if Party Voice Chat plugin is loaded."
		);
		if (partyVoiceChatPlugin == null) {
			VoiceChatListener.log.error(
				"Party Voice Chat plugin is not loaded! Cancel event handling."
			);
			return;
		}

		VoiceChatListener.log.error("Get Unite party provider.");
		final @Nullable PartyProvider partyProvider =
			partyVoiceChatPlugin.getPartyProvider();
		if (partyProvider == null) {
			VoiceChatListener.log.error(
				"Party provider not loaded! Cancel event handling."
			);
			return;
		}
	}
}
