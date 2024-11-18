package world.urelion.partyvoicechat;

import de.maxhenkel.voicechat.api.VoicechatApi;
import de.maxhenkel.voicechat.api.VoicechatPlugin;
import de.maxhenkel.voicechat.api.events.Event;
import de.maxhenkel.voicechat.api.events.EventRegistration;
import lombok.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import world.urelion.UrelionPlugin;

@Slf4j
@Singleton(style = Singleton.Style.HOLDER)
public class PartyVoiceChatPlugin
extends UrelionPlugin
implements VoicechatPlugin {
	/**
	 * @return the unique identifier of this {@link VoicechatPlugin}
	 *
	 * @since 1.0.0
	 */
	@Override
	public @NotNull String getPluginId() {
		return this.getName();
	}

	/**
	 * called after this {@link VoicechatPlugin} was loaded
	 *
	 * @param voicechatApi the {@link VoicechatApi} to use Voice Chat
	 *
	 * @since 1.0.0
	 */
	@Override
	public void initialize(
		final @Nullable VoicechatApi voicechatApi
	) {
		if (voicechatApi == null) {
			return;
		}
	}

	/**
	 * register {@link Event}s
	 *
	 * @param eventRegistration the event registration object,
	 *                          used to register events
	 *
	 * @since 1.0.0
	 */
	@Override
	public void registerEvents(
		final @Nullable EventRegistration eventRegistration
	) {
		if (eventRegistration == null) {
			return;
		}
	}
}
