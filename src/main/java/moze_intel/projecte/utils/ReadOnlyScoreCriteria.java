package moze_intel.projecte.utils;

import net.minecraft.world.scores.criteria.ObjectiveCriteria;

import net.minecraft.world.scores.criteria.ObjectiveCriteria.RenderType;

public class ReadOnlyScoreCriteria extends ObjectiveCriteria {

	// Expose protected superclass ctor
	public ReadOnlyScoreCriteria(String name) {
		super(name, true, RenderType.INTEGER);
	}
}