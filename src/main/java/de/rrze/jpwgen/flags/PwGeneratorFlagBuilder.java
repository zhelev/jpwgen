package de.rrze.jpwgen.flags;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import de.rrze.jpwgen.IPwFlag;
import de.rrze.jpwgen.flags.impl.AtLeast2DigitsFlag;
import de.rrze.jpwgen.flags.impl.AtLeast2SymbolsFlag;
import de.rrze.jpwgen.flags.impl.EndsNoDigitFlag;
import de.rrze.jpwgen.flags.impl.EndsNoSmallLetterFlag;
import de.rrze.jpwgen.flags.impl.EndsNoSymbolFlag;
import de.rrze.jpwgen.flags.impl.EndsNoUpperLetterFlag;
import de.rrze.jpwgen.flags.impl.Only1CapitalFlag;
import de.rrze.jpwgen.flags.impl.Only1DigitFlag;
import de.rrze.jpwgen.flags.impl.Only1SymbolFlag;
import de.rrze.jpwgen.flags.impl.PwAmbiguousFlag;
import de.rrze.jpwgen.flags.impl.PwCapitalizeFlag;
import de.rrze.jpwgen.flags.impl.PwNumeralsFlag;
import de.rrze.jpwgen.flags.impl.PwReducedSymbolsFlag;
import de.rrze.jpwgen.flags.impl.PwSymbolsFlag;
import de.rrze.jpwgen.flags.impl.StartsNoDigitFlag;
import de.rrze.jpwgen.flags.impl.StartsNoSmallLetterFlag;
import de.rrze.jpwgen.flags.impl.StartsNoSymbolFlag;
import de.rrze.jpwgen.flags.impl.StartsNoUpperLetterFlag;

public class PwGeneratorFlagBuilder implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final HashMap<String, IPwFlag> FLAGS = new HashMap<String, IPwFlag>();

	static {
		FLAGS.put("IncludeCapitals", new PwCapitalizeFlag());
		FLAGS.put("IncludeNumerals", new PwNumeralsFlag());
		FLAGS.put("IncludeSymbols", new PwSymbolsFlag());
		FLAGS.put("IncludeReducedSymbols", new PwReducedSymbolsFlag());
		FLAGS.put("FilterAmbiguous", new PwAmbiguousFlag());
		FLAGS.put("DoNotStartWithSmallLetter", new StartsNoSmallLetterFlag());
		FLAGS.put("DoNotEndWithSmallLetter", new EndsNoSmallLetterFlag());
		FLAGS.put("DoNotStartWithUpperLetter", new StartsNoUpperLetterFlag());
		FLAGS.put("DoNotEndWithUpperLetter", new EndsNoUpperLetterFlag());
		FLAGS.put("DoNotStartWithDigit", new StartsNoDigitFlag());
		FLAGS.put("DoNotEndWithDigit", new EndsNoDigitFlag());
		FLAGS.put("DoNotStartsWithSymbol", new StartsNoSymbolFlag());
		FLAGS.put("DoNotEndWithSymbol", new EndsNoSymbolFlag());
		FLAGS.put("Only1Capital", new Only1CapitalFlag());
		FLAGS.put("Only1Symbol", new Only1SymbolFlag());
		FLAGS.put("AtLeast2Symbols", new AtLeast2SymbolsFlag());
		FLAGS.put("Only1Digit", new Only1DigitFlag());
		FLAGS.put("AtLeast2Digits", new AtLeast2DigitsFlag());
	}

	List<IPwFlag> flagList = new ArrayList<IPwFlag>();

	int flags = 0;

	public PwGeneratorFlagBuilder() {
	}

	public PwGeneratorFlagBuilder(int flags) {
		this.flags = flags;
	}

	public PwGeneratorFlagBuilder setIncludeCapitals() {
		flagList.add(FLAGS.get("IncludeCapitals"));
		return this;
	}

	public PwGeneratorFlagBuilder setIncludeNumerals() {
		flagList.add(FLAGS.get("IncludeNumerals"));
		return this;
	}

	public PwGeneratorFlagBuilder setIncludeSymbols() {
		flagList.add(FLAGS.get("IncludeSymbols"));
		return this;
	}

	public PwGeneratorFlagBuilder setIncludeReducedSymbols() {
		flagList.add(FLAGS.get("IncludeReducedSymbols"));
		return this;
	}

	public PwGeneratorFlagBuilder setFilterAmbiguous() {
		flagList.add(FLAGS.get("FilterAmbiguous"));
		return this;
	}

	public PwGeneratorFlagBuilder setDoNotStartWithSmallLetter() {
		flagList.add(FLAGS.get("DoNotStartWithSmallLetter"));
		return this;
	}

	public PwGeneratorFlagBuilder setDoNotEndWithSmallLetter() {
		flagList.add(FLAGS.get("DoNotEndWithSmallLetter"));
		return this;
	}

	public PwGeneratorFlagBuilder setDoNotStartWithUpperLetter() {
		flagList.add(FLAGS.get("DoNotStartWithUpperLetter"));
		return this;
	}

	public PwGeneratorFlagBuilder setDoNotEndWithUpperLetter() {
		flagList.add(FLAGS.get("DoNotEndWithUpperLetter"));
		return this;
	}

	public PwGeneratorFlagBuilder setDoNotStartWithDigit() {
		flagList.add(FLAGS.get("DoNotStartWithDigit"));
		return this;
	}

	public PwGeneratorFlagBuilder setDoNotEndWithDigit() {
		flagList.add(FLAGS.get("DoNotEndWithDigit"));
		return this;
	}

	public PwGeneratorFlagBuilder setDoNotStartsWithSymbol() {
		flagList.add(FLAGS.get("DoNotStartsWithSymbol"));
		return this;
	}

	public PwGeneratorFlagBuilder setDoNotEndWithSymbol() {
		flagList.add(FLAGS.get("DoNotEndWithSymbol"));
		return this;
	}

	public PwGeneratorFlagBuilder setOnly1Capital() {
		flagList.add(FLAGS.get("Only1Capital"));
		return this;
	}

	public PwGeneratorFlagBuilder setOnly1Symbol() {
		flagList.add(FLAGS.get("Only1Symbol"));
		return this;
	}

	public PwGeneratorFlagBuilder setAtLeast2Symbols() {
		flagList.add(FLAGS.get("AtLeast2Symbols"));
		return this;
	}

	public PwGeneratorFlagBuilder setOnly1Digit() {
		flagList.add(FLAGS.get("Only1Digit"));
		return this;
	}

	public PwGeneratorFlagBuilder setAtLeast2Digits() {
		flagList.add(FLAGS.get("AtLeast2Digits"));
		return this;
	}

	public synchronized int build() {
		int build = flags;

		for (IPwFlag fl : flagList) {
			build = fl.mask(build);
		}

		return build;
	}
	
	public synchronized List<String> applied() {
		
		List<String> applied = new ArrayList<String>();

		Set<String> keys = FLAGS.keySet();
		for (String key : keys) {
			if (FLAGS.get(key).isMasked(build()))
				applied.add(key);
		}

		return applied;
	}
	
	public static synchronized List<String> evalFlags(int flags) {
		
		List<String> applied = new ArrayList<String>();

		Set<String> keys = FLAGS.keySet();
		for (String key : keys) {
			if (FLAGS.get(key).isMasked(flags))
				applied.add(key);
		}

		return applied;
	}

}
