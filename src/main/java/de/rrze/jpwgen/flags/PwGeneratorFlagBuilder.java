package de.rrze.jpwgen.flags;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import de.rrze.jpwgen.ICliFlag;
import de.rrze.jpwgen.IPwFlag;

public class PwGeneratorFlagBuilder implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final HashMap<String, IPwFlag> SUPPORTED_FLAGS = new HashMap<String, IPwFlag>();

	static {
		SUPPORTED_FLAGS.put(IPwFlag.PW_CAPITALS_FLAG.getId(),
				IPwFlag.PW_CAPITALS_FLAG);

		SUPPORTED_FLAGS.put(IPwFlag.PW_DIGITS_FLAG.getId(),
				IPwFlag.PW_DIGITS_FLAG);

		SUPPORTED_FLAGS.put(IPwFlag.PW_SYMBOLS_FLAG.getId(),
				IPwFlag.PW_SYMBOLS_FLAG);

		SUPPORTED_FLAGS.put(IPwFlag.PW_REDUCED_SYMBOLS_FLAG.getId(),
				IPwFlag.PW_REDUCED_SYMBOLS_FLAG);

		SUPPORTED_FLAGS.put(IPwFlag.PW_AMBIGOUS_FLAG.getId(),
				IPwFlag.PW_AMBIGOUS_FLAG);

		SUPPORTED_FLAGS.put(IPwFlag.STARTS_NO_LOWERCASE_FLAG.getId(),
				IPwFlag.STARTS_NO_LOWERCASE_FLAG);

		SUPPORTED_FLAGS.put(IPwFlag.ENDS_NO_LOWERCASE_FLAG.getId(),
				IPwFlag.ENDS_NO_LOWERCASE_FLAG);

		SUPPORTED_FLAGS.put(IPwFlag.STARTS_NO_UPPERCASE_FLAG.getId(),
				IPwFlag.STARTS_NO_UPPERCASE_FLAG);

		SUPPORTED_FLAGS.put(IPwFlag.ENDS_NO_UPPERCASE_FLAG.getId(),
				IPwFlag.ENDS_NO_UPPERCASE_FLAG);

		SUPPORTED_FLAGS.put(IPwFlag.STARTS_NO_DIGIT_FLAG.getId(),
				IPwFlag.STARTS_NO_DIGIT_FLAG);

		SUPPORTED_FLAGS.put(IPwFlag.ENDS_NO_DIGIT_FLAG.getId(),
				IPwFlag.ENDS_NO_DIGIT_FLAG);

		SUPPORTED_FLAGS.put(IPwFlag.STARTS_NO_SYMBOL_FLAG.getId(),
				IPwFlag.STARTS_NO_SYMBOL_FLAG);

		SUPPORTED_FLAGS.put(IPwFlag.ENDS_NO_SYMBOL_FLAG.getId(),
				IPwFlag.ENDS_NO_SYMBOL_FLAG);

		SUPPORTED_FLAGS.put(IPwFlag.SINGLE_CAPITAL_FLAG.getId(),
				IPwFlag.SINGLE_CAPITAL_FLAG);

		SUPPORTED_FLAGS.put(IPwFlag.SINGLE_SYMBOL_FLAG.getId(),
				IPwFlag.SINGLE_SYMBOL_FLAG);

		SUPPORTED_FLAGS.put(IPwFlag.AT_LEAST_TWO_SYMBOLS_FLAG.getId(),
				IPwFlag.AT_LEAST_TWO_SYMBOLS_FLAG);

		SUPPORTED_FLAGS.put(IPwFlag.SINGLE_DIGIT_FLAG.getId(),
				IPwFlag.SINGLE_DIGIT_FLAG);

		SUPPORTED_FLAGS.put(IPwFlag.AT_LEAST_TWO_DIGITS_FLAG.getId(),
				IPwFlag.AT_LEAST_TWO_DIGITS_FLAG);

		SUPPORTED_FLAGS.put(IPwFlag.CONSEC_CAPITALS_FLAG.getId(),
				IPwFlag.CONSEC_CAPITALS_FLAG);

		SUPPORTED_FLAGS.put(IPwFlag.CONSEC_DIGITS_FLAG.getId(),
				IPwFlag.CONSEC_DIGITS_FLAG);

		SUPPORTED_FLAGS.put(IPwFlag.CONSEC_SYMBOLS_FLAG.getId(),
				IPwFlag.CONSEC_SYMBOLS_FLAG);

	}

	List<IPwFlag> flagList = new ArrayList<IPwFlag>();

	Long flags = 0l;

	public PwGeneratorFlagBuilder() {
	}

	public PwGeneratorFlagBuilder(Long flags) {
		this.flagList = loadFlags(evalFlags(flags));
		if(build()!=flags)
			throw new IllegalArgumentException("Cannot rebuild flags as options.");
		
		this.flags = flags;
	}

	public PwGeneratorFlagBuilder(List<IPwFlag> flagList) {
		this.flagList = flagList;

		this.flags = build();
	}

	public PwGeneratorFlagBuilder setIncludeCapitals() {
		flagList.add(IPwFlag.PW_CAPITALS_FLAG);
		return this;
	}

	public PwGeneratorFlagBuilder setIncludeDigits() {
		flagList.add(IPwFlag.PW_DIGITS_FLAG);
		return this;
	}

	public PwGeneratorFlagBuilder setIncludeSymbols() {
		flagList.add(IPwFlag.PW_SYMBOLS_FLAG);
		return this;
	}

	public PwGeneratorFlagBuilder setIncludeReducedSymbols() {
		flagList.add(IPwFlag.PW_REDUCED_SYMBOLS_FLAG);
		return this;
	}

	public PwGeneratorFlagBuilder setFilterAmbiguous() {
		flagList.add(IPwFlag.PW_AMBIGOUS_FLAG);
		return this;
	}

	public PwGeneratorFlagBuilder setDoNotStartWithLowercase() {
		flagList.add(IPwFlag.STARTS_NO_LOWERCASE_FLAG);
		return this;
	}

	public PwGeneratorFlagBuilder setDoNotEndWithLowercase() {
		flagList.add(IPwFlag.ENDS_NO_LOWERCASE_FLAG);
		return this;
	}

	public PwGeneratorFlagBuilder setDoNotStartWithUppercase() {
		flagList.add(IPwFlag.STARTS_NO_UPPERCASE_FLAG);
		return this;
	}

	public PwGeneratorFlagBuilder setDoNotEndWithUppercase() {
		flagList.add(IPwFlag.ENDS_NO_UPPERCASE_FLAG);
		return this;
	}

	public PwGeneratorFlagBuilder setDoNotStartWithDigit() {
		flagList.add(IPwFlag.STARTS_NO_DIGIT_FLAG);
		return this;
	}

	public PwGeneratorFlagBuilder setDoNotEndWithDigit() {
		flagList.add(IPwFlag.ENDS_NO_DIGIT_FLAG);
		return this;
	}

	public PwGeneratorFlagBuilder setDoNotStartsWithSymbol() {
		flagList.add(IPwFlag.STARTS_NO_SYMBOL_FLAG);
		return this;
	}

	public PwGeneratorFlagBuilder setDoNotEndWithSymbol() {
		flagList.add(IPwFlag.ENDS_NO_SYMBOL_FLAG);
		return this;
	}

	public PwGeneratorFlagBuilder setOnly1Capital() {
		flagList.add(IPwFlag.SINGLE_CAPITAL_FLAG);
		return this;
	}

	public PwGeneratorFlagBuilder setOnly1Symbol() {
		flagList.add(IPwFlag.SINGLE_SYMBOL_FLAG);
		return this;
	}

	public PwGeneratorFlagBuilder setAtLeast2Symbols() {
		flagList.add(IPwFlag.AT_LEAST_TWO_SYMBOLS_FLAG);
		return this;
	}

	public PwGeneratorFlagBuilder setOnly1Digit() {
		flagList.add(IPwFlag.SINGLE_DIGIT_FLAG);
		return this;
	}

	public PwGeneratorFlagBuilder setAtLeast2Digits() {
		flagList.add(IPwFlag.AT_LEAST_TWO_DIGITS_FLAG);
		return this;
	}

	public PwGeneratorFlagBuilder setDisableConsecCapitals() {
		flagList.add(IPwFlag.CONSEC_CAPITALS_FLAG);
		return this;
	}

	public PwGeneratorFlagBuilder setDisableConsecSymbols() {
		flagList.add(IPwFlag.CONSEC_SYMBOLS_FLAG);
		return this;
	}

	public PwGeneratorFlagBuilder setDisableConsecDigits() {
		flagList.add(IPwFlag.CONSEC_DIGITS_FLAG);
		return this;
	}

	public synchronized Long build() {
		Long build = flags;

		for (IPwFlag fl : flagList) {
			build = fl.mask(build);
		}

		return build;
	}

	public synchronized List<String> applied() {

		List<String> applied = new ArrayList<String>();

		Set<String> keys = SUPPORTED_FLAGS.keySet();
		for (String key : keys) {
			if (SUPPORTED_FLAGS.get(key).isMasked(build()))
				applied.add(key);
		}

		return applied;
	}

	public static synchronized List<String> evalFlags(Long flags) {

		List<String> applied = new ArrayList<String>();

		Set<String> keys = SUPPORTED_FLAGS.keySet();
		for (String key : keys) {
			if (SUPPORTED_FLAGS.get(key).isMasked(flags))
				applied.add(key);
		}

		return applied;
	}

	public static synchronized List<IPwFlag> loadFlags(List<String> flagList) {
		List<IPwFlag> flags = new ArrayList<IPwFlag>();

		for (String flag : flagList) {
			IPwFlag fl = SUPPORTED_FLAGS.get(flag);
			if (fl != null)
				flags.add(fl);
			else
				throw new RuntimeException("Unknown option: " + flag);
		}

		return flags;
	}

	public static List<ICliFlag> getCliFlags() {
		List<ICliFlag> clis = new ArrayList<ICliFlag>();

		for (IPwFlag flag : SUPPORTED_FLAGS.values()) {
			if (flag instanceof ICliFlag) {
				ICliFlag cli = (ICliFlag) flag;
				clis.add(cli);
			}

		}
		return clis;
	}

	public static List<String> getCliShorts() {
		List<String> shorts = new ArrayList<String>();

		List<ICliFlag> clis = getCliFlags();
		for (ICliFlag iCliFlag : clis) {
			shorts.add(iCliFlag.cliShort());
			if (iCliFlag.cliShortDisable() != null)
				shorts.add(iCliFlag.cliShortDisable());
		}

		Collections.sort(shorts, null);

		return shorts;
	}

	public static List<String> getCliLongs() {
		List<String> longs = new ArrayList<String>();

		List<ICliFlag> clis = getCliFlags();
		for (ICliFlag iCliFlag : clis) {
			longs.add(iCliFlag.cliLong());
			if (iCliFlag.cliLongDisable() != null)
				longs.add(iCliFlag.cliLongDisable());
		}

		Collections.sort(longs, null);

		return longs;
	}
}
