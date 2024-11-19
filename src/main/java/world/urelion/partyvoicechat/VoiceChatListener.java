package world.urelion.partyvoicechat;

import de.maxhenkel.voicechat.api.ServerPlayer;
import de.maxhenkel.voicechat.api.VoicechatConnection;
import de.maxhenkel.voicechat.api.VoicechatServerApi;
import de.maxhenkel.voicechat.api.events.MicrophonePacketEvent;
import de.maxhenkel.voicechat.api.packets.MicrophonePacket;
import de.maxhenkel.voicechat.api.packets.StaticSoundPacket;
import lombok.extern.slf4j.Slf4j;
import me.pikamug.unite.api.objects.PartyProvider;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

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

		VoiceChatListener.log.trace("Get sender connection.");
		final @Nullable VoicechatConnection senderConnection =
			event.getSenderConnection();
		VoiceChatListener.log.trace(
			"Check if sender connection is established."
		);
		if (senderConnection == null) {
			VoiceChatListener.log.error(
				"Sender connection not established! Cancel event handling."
			);
			return;
		}

		VoiceChatListener.log.trace("Get sender as server player.");
		final @Nullable ServerPlayer serverPlayer =
			senderConnection.getPlayer();
		VoiceChatListener.log.trace("Check if server player is online.");
		if (serverPlayer == null) {
			VoiceChatListener.log.error(
				"Server player not online! Cancel event handling."
			);
			return;
		}

		VoiceChatListener.log.trace("Get player of sender.");
		final @Nullable Object playerObject = serverPlayer.getPlayer();
		VoiceChatListener.log.trace("Check if sender is a player.");
		if (playerObject instanceof @NotNull Player player) {
			VoiceChatListener.log.trace("Check if player is in a party.");
			if (partyProvider.isPlayerInParty(player)) {
				VoiceChatListener.log.trace("Get party of sender.");
				final @Nullable String partyId = partyProvider.getPartyId(
					player.getUniqueId()
				);
				VoiceChatListener.log.trace("Check if party exists.");
				if (partyId == null) {
					VoiceChatListener.log.warn(
						"Party does not exist! Cancel event handling."
					);
					return;
				}

				VoiceChatListener.log.trace("Get Voice Chat API.");
				final @Nullable VoicechatServerApi voicechat =
					event.getVoicechat();
				VoiceChatListener.log.trace(
					"Check if Voice Chat server API is loaded."
				);
				if (voicechat == null) {
					VoiceChatListener.log.error(
						"Voice Chat not loaded! Cancel event handling."
					);
					return;
				}

				VoiceChatListener.log.trace("Get microphone packet.");
				final @Nullable MicrophonePacket microphonePacket =
					event.getPacket();
				VoiceChatListener.log.trace(
					"Check if microphone packet exists."
				);
				if (microphonePacket == null) {
					VoiceChatListener.log.warn(
						"Microphone packet not exists! Cancel event handling."
					);
					return;
				}
				VoiceChatListener.log.trace(
					"Get static sound packet from microphone packet."
				);
				final @Nullable StaticSoundPacket staticSoundPacket =
					microphonePacket.toStaticSoundPacket();
				VoiceChatListener.log.trace(
					"Check if static sound packet exists."
				);
				if (staticSoundPacket == null) {
					VoiceChatListener.log.warn(
						"Static sound packet not exists! Cancel event handling."
					);
					return;
				}

				VoiceChatListener.log.trace(
					"Iterate of all online party members."
				);
				for (
					final @NotNull UUID memberUuid :
					partyProvider.getOnlineMembers(partyId)
				) {
					VoiceChatListener.log.trace(
						"Get Voice Chat connection of party member."
					);
					final @Nullable VoicechatConnection memberConnection =
						voicechat.getConnectionOf(memberUuid);
					VoiceChatListener.log.trace(
						"Send static sound packet to party member."
					);
					voicechat.sendStaticSoundPacketTo(
						memberConnection,
						staticSoundPacket
					);
				}
			}
		}
	}
}
