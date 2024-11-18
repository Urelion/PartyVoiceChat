package world.urelion.partyvoicechat;

import de.maxhenkel.voicechat.api.BukkitVoicechatService;
import de.maxhenkel.voicechat.api.Player;
import de.maxhenkel.voicechat.api.VoicechatApi;
import de.maxhenkel.voicechat.api.VoicechatPlugin;
import de.maxhenkel.voicechat.api.events.Event;
import de.maxhenkel.voicechat.api.events.EventRegistration;
import lombok.Getter;
import lombok.Singleton;
import lombok.extern.slf4j.Slf4j;
import me.pikamug.unite.Unite;
import me.pikamug.unite.api.objects.PartyProvider;
import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicesManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import world.urelion.UrelionPlugin;

/**
 * main class of the {@link PartyVoiceChatPlugin},
 * which link all {@link Player}s of one party in Voice Chat
 *
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
@Singleton(style = Singleton.Style.HOLDER)
public class PartyVoiceChatPlugin
extends UrelionPlugin
implements VoicechatPlugin {
	/**
	 * {@link PartyProvider} to get party information by {@link Unite}
	 *
	 * @since 1.0.0
	 */
	@Getter
	private @Nullable PartyProvider partyProvider = null;

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

	/**
	 * enables the {@link PartyVoiceChatPlugin}
	 *
	 * @since 1.0.0
	 */
	@Override public void whileEnable() {
		super.whileEnable();

		final @NotNull Server server = this.getServer();

		final @NotNull PluginManager pluginManager = server.getPluginManager();
		final @NotNull ServicesManager servicesManager =
			server.getServicesManager();

		if (pluginManager.getPlugin("Unite") != null) {
			final @Nullable
			RegisteredServiceProvider<PartyProvider> registeredServiceProvider =
				servicesManager.getRegistration(PartyProvider.class);

			if (registeredServiceProvider != null) {
				this.partyProvider = registeredServiceProvider.getProvider();
			}
		}

		final @Nullable BukkitVoicechatService service =
			server.getServicesManager().load(
				BukkitVoicechatService.class
			);
		if (service != null) {
			service.registerPlugin(this);
		}
	}

	/**
	 * disables the {@link PartyVoiceChatPlugin}
	 *
	 * @since 1.0.0
	 */
	@Override public void whileDisable() {
		super.whileDisable();

		this.getServer().getServicesManager().unregister(this);
	}
}
