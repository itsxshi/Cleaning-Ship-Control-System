package clean.ship61.absim.rule;

/**
 * Create Rule instance accounting to the type.
 *
 *
 */
public class ABRuleFactory {
	
	public static IABRule create(RuleType ruleType) {
		
		switch (ruleType) {
		case MaxValue:
			return new ABRuleMaxValueCell();
		case Nearest:
			return new ABRuleNearestCell();
		default:
			return null;
		}
		
	}

}