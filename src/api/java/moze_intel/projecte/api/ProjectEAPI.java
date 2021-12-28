package moze_intel.projecte.api;

import moze_intel.projecte.api.capabilities.IAlchBagProvider;
import moze_intel.projecte.api.capabilities.IKnowledgeProvider;
import moze_intel.projecte.api.capabilities.item.IAlchBagItem;
import moze_intel.projecte.api.capabilities.item.IAlchChestItem;
import moze_intel.projecte.api.capabilities.item.IExtraFunction;
import moze_intel.projecte.api.capabilities.item.IItemCharge;
import moze_intel.projecte.api.capabilities.item.IItemEmcHolder;
import moze_intel.projecte.api.capabilities.item.IModeChanger;
import moze_intel.projecte.api.capabilities.item.IPedestalItem;
import moze_intel.projecte.api.capabilities.item.IProjectileShooter;
import moze_intel.projecte.api.capabilities.tile.IEmcStorage;
import moze_intel.projecte.api.proxy.IEMCProxy;
import moze_intel.projecte.api.proxy.ITransmutationProxy;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class ProjectEAPI {

	private static IEMCProxy emcProxy;
	private static ITransmutationProxy transProxy;
	private static final Logger LOGGER = LogManager.getLogger("projecteapi");
	public static final String PROJECTE_MODID = "projecte";

	private ProjectEAPI() {
	}

	/**
	 * The capability object for IEmcStorage
	 */
	public static final Capability<IEmcStorage> EMC_STORAGE_CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});

	/**
	 * The capability object for IAlchBagProvider
	 */
	public static final Capability<IAlchBagProvider> ALCH_BAG_CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});

	/**
	 * The capability object for IKnowledgeProvider
	 */
	public static final Capability<IKnowledgeProvider> KNOWLEDGE_CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});

	/**
	 * The capability object for IAlchBagItem
	 */
	public static final Capability<IAlchBagItem> ALCH_BAG_ITEM_CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});

	/**
	 * The capability object for IAlchChestItem
	 */
	public static final Capability<IAlchChestItem> ALCH_CHEST_ITEM_CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});

	/**
	 * The capability object for IExtraFunction
	 */
	public static final Capability<IExtraFunction> EXTRA_FUNCTION_ITEM_CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});

	/**
	 * The capability object for IItemCharge
	 */
	public static final Capability<IItemCharge> CHARGE_ITEM_CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});

	/**
	 * The capability object for IItemEmcHolder
	 */
	public static final Capability<IItemEmcHolder> EMC_HOLDER_ITEM_CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});

	/**
	 * The capability object for IModeChanger
	 */
	public static final Capability<IModeChanger> MODE_CHANGER_ITEM_CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});

	/**
	 * The capability object for IPedestalItem
	 */
	public static final Capability<IPedestalItem> PEDESTAL_ITEM_CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});

	/**
	 * The capability object for IProjectileShooter
	 */
	public static final Capability<IProjectileShooter> PROJECTILE_SHOOTER_ITEM_CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});

	/**
	 * Retrieves the proxy for EMC-based API queries.
	 *
	 * @return The proxy for EMC-based API queries
	 */
	public static IEMCProxy getEMCProxy() {
		// Harmless race
		if (emcProxy == null) {
			try {
				Class<?> clazz = Class.forName("moze_intel.projecte.impl.EMCProxyImpl");
				emcProxy = (IEMCProxy) clazz.getField("instance").get(null);
			} catch (ReflectiveOperationException ex) {
				LOGGER.warn("Error retrieving EMCProxyImpl, ProjectE may be absent, damaged, or outdated.");
			}
		}
		return emcProxy;
	}

	/**
	 * Retrieves the proxy for Transmutation-based API queries.
	 *
	 * @return The proxy for Transmutation-based API queries
	 */
	public static ITransmutationProxy getTransmutationProxy() {
		// Harmless race
		if (transProxy == null) {
			try {
				Class<?> clazz = Class.forName("moze_intel.projecte.impl.TransmutationProxyImpl");
				transProxy = (ITransmutationProxy) clazz.getField("instance").get(null);
			} catch (ReflectiveOperationException ex) {
				LOGGER.warn("Error retrieving TransmutationProxyImpl, ProjectE may be absent, damaged, or outdated.");
			}
		}
		return transProxy;
	}
}