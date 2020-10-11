package com.ghisooo;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;

public class App {
	private static Map<String, Stock> stocks;
	private static HashMap<String, Double> stockPrices = new HashMap<String, Double>();
	private static final Pattern p = Pattern.compile("\\(([^)]+)\\)");

	public static void main(String[] args) {
		HashMap<String, Object> parameter = new HashMap<String, Object>();
		handleCommand(args, parameter);
		String[] symbols = new String[] { "QQQ", "ARKK", "IBB", "NVDA", "MRK", "GILD", "TLT", "IEF", "GLDM" };

		try {
			stocks = YahooFinance.get(symbols);
			for (String symbol : symbols) {
				stockPrices.put(symbol, getPrice(symbol));
			}
			switch ((String) (parameter.get("preference"))) {
			case "a":
			case "A":
				System.out.println("\n [Option1] QQQ 30%:" + (Double) parameter.get("money") * 0.3
						+ ", the number of stocks that I can buy:"
						+ (Double) parameter.get("money") * 0.3 / stockPrices.get("QQQ") + "\nARKK 30%:"
						+ (Double) parameter.get("money") * 0.3 + ", the number of stocks that I can buy:"
						+ (Double) parameter.get("money") * 0.3 / stockPrices.get("ARKK") + "\nIBB 30%:"
						+ (Double) parameter.get("money") * 0.3 + ", the number of stocks that I can buy:"
						+ (Double) parameter.get("money") * 0.3 / stockPrices.get("IBB") + "\nRest:"
						+ ((Double) parameter.get("money") * 0.1
								+ getMoneyAfterBuy((Double) parameter.get("money"), 0.3, "QQQ")
								+ getMoneyAfterBuy((Double) parameter.get("money"), 0.3, "ARKK")
								+ getMoneyAfterBuy((Double) parameter.get("money"), 0.3, "IBB")));

				System.out.println("\n [Option2] QQQ 30%:" + (Double) parameter.get("money") * 0.3
						+ ", the number of stocks that I can buy:"
						+ (Double) parameter.get("money") * 0.3 / stockPrices.get("QQQ") + "\nARKK 30%:"
						+ (Double) parameter.get("money") * 0.3 + ", the number of stocks that I can buy:"
						+ (Double) parameter.get("money") * 0.3 / stockPrices.get("ARKK") + "\nIBB 25%:"
						+ (Double) parameter.get("money") * 0.25 + ", the number of stocks that I can buy:"
						+ (Double) parameter.get("money") * 0.25 / stockPrices.get("IBB") + "\nRest:"
						+ ((Double) parameter.get("money") * 0.15
								+ getMoneyAfterBuy((Double) parameter.get("money"), 0.3, "QQQ")
								+ getMoneyAfterBuy((Double) parameter.get("money"), 0.3, "ARKK")
								+ getMoneyAfterBuy((Double) parameter.get("money"), 0.25, "IBB")));

				System.out.println("\n [Option3] QQQ 30%:" + (Double) parameter.get("money") * 0.3
						+ ", the number of stocks that I can buy:"
						+ (Double) parameter.get("money") * 0.3 / stockPrices.get("QQQ") + "\nARKK 25%:"
						+ (Double) parameter.get("money") * 0.25 + ", the number of stocks that I can buy:"
						+ (Double) parameter.get("money") * 0.25 / stockPrices.get("ARKK") + "\nIBB 20%:"
						+ (Double) parameter.get("money") * 0.20 + ", the number of stocks that I can buy:"
						+ (Double) parameter.get("money") * 0.20 / stockPrices.get("IBB") + "\nRest:"
						+ ((Double) parameter.get("money") * 0.25
								+ getMoneyAfterBuy((Double) parameter.get("money"), 0.3, "QQQ")
								+ getMoneyAfterBuy((Double) parameter.get("money"), 0.25, "ARKK")
								+ getMoneyAfterBuy((Double) parameter.get("money"), 0.20, "IBB")));

				System.out.println("\n [Option4] QQQ 30%:" + (Double) parameter.get("money") * 0.3
						+ ", the number of stocks that I can buy:"
						+ (Double) parameter.get("money") * 0.3 / stockPrices.get("QQQ") + "\nARKK 20%:"
						+ (Double) parameter.get("money") * 0.2 + ", the number of stocks that I can buy:"
						+ (Double) parameter.get("money") * 0.20 / stockPrices.get("ARKK") + "\nIBB 20%:"
						+ (Double) parameter.get("money") * 0.20 + ", the number of stocks that I can buy:"
						+ (Double) parameter.get("money") * 0.20 / stockPrices.get("IBB") + "\nRest:"
						+ ((Double) parameter.get("money") * 0.3
								+ getMoneyAfterBuy((Double) parameter.get("money"), 0.3, "QQQ")
								+ getMoneyAfterBuy((Double) parameter.get("money"), 0.20, "ARKK")
								+ getMoneyAfterBuy((Double) parameter.get("money"), 0.20, "IBB")));
				break;

			case "n":
			case "N":
				System.out.println("\n [Option1] TLT 10%:" + (Double) parameter.get("money") * 0.1
						+ ", the number of stocks that I can buy:"
						+ (Double) parameter.get("money") * 0.1 / stockPrices.get("TLT") + "\nIEF 10%:"
						+ (Double) parameter.get("money") * 0.1 + ", the number of stocks that I can buy:"
						+ (Double) parameter.get("money") * 0.1 / stockPrices.get("IEF") + "\nGLDM 5%:"
						+ (Double) parameter.get("money") * 0.05 + ", the number of stocks that I can buy:"
						+ (Double) parameter.get("money") * 0.05 / stockPrices.get("GLDM") + "\nQQQ 25%:"
						+ (Double) parameter.get("money") * 0.25 + ", the number of stocks that I can buy:"
						+ (Double) parameter.get("money") * 0.25 / stockPrices.get("QQQ") + "\nARKK 25%:"
						+ (Double) parameter.get("money") * 0.25 + ", the number of stocks that I can buy:"
						+ (Double) parameter.get("money") * 0.25 / stockPrices.get("ARKK") + "\nIBB 20%:"
						+ (Double) parameter.get("money") * 0.2 + ", the number of stocks that I can buy:"
						+ (Double) parameter.get("money") * 0.2 / stockPrices.get("IBB") + "\nRest:"
						+ ((Double) parameter.get("money") * 0.05
								+ getMoneyAfterBuy((Double) parameter.get("money"), 0.1, "TLT")
								+ getMoneyAfterBuy((Double) parameter.get("money"), 0.1, "IEF")
								+ getMoneyAfterBuy((Double) parameter.get("money"), 0.05, "GLDM")
								+ getMoneyAfterBuy((Double) parameter.get("money"), 0.25, "QQQ")
								+ getMoneyAfterBuy((Double) parameter.get("money"), 0.25, "ARKK")
								+ getMoneyAfterBuy((Double) parameter.get("money"), 0.2, "IBB")));
				System.out.println("\n [Option2] TLT 10%:" + (Double) parameter.get("money") * 0.1
						+ ", the number of stocks that I can buy:"
						+ (Double) parameter.get("money") * 0.1 / stockPrices.get("TLT") + "\nIEF 10%:"
						+ (Double) parameter.get("money") * 0.1 + ", the number of stocks that I can buy:"
						+ (Double) parameter.get("money") * 0.1 / stockPrices.get("IEF") + "\nGLDM 5%:"
						+ (Double) parameter.get("money") * 0.05 + ", the number of stocks that I can buy:"
						+ (Double) parameter.get("money") * 0.05 / stockPrices.get("GLDM") + "\nQQQ 25%:"
						+ (Double) parameter.get("money") * 0.25 + ", the number of stocks that I can buy:"
						+ (Double) parameter.get("money") * 0.25 / stockPrices.get("QQQ") + "\nARKK 20%:"
						+ (Double) parameter.get("money") * 0.20 + ", the number of stocks that I can buy:"
						+ (Double) parameter.get("money") * 0.20 / stockPrices.get("ARKK") + "\nIBB 20%:"
						+ (Double) parameter.get("money") * 0.2 + ", the number of stocks that I can buy:"
						+ (Double) parameter.get("money") * 0.20 / stockPrices.get("IBB") + "\nRest:"
						+ ((Double) parameter.get("money") * 0.1
								+ getMoneyAfterBuy((Double) parameter.get("money"), 0.1, "TLT")
								+ getMoneyAfterBuy((Double) parameter.get("money"), 0.1, "IEF")
								+ getMoneyAfterBuy((Double) parameter.get("money"), 0.05, "GLDM")
								+ getMoneyAfterBuy((Double) parameter.get("money"), 0.25, "QQQ")
								+ getMoneyAfterBuy((Double) parameter.get("money"), 0.2, "ARKK")
								+ getMoneyAfterBuy((Double) parameter.get("money"), 0.2, "IBB")));
				System.out.println("\n [Option3] TLT 10%:" + (Double) parameter.get("money") * 0.1
						+ ", the number of stocks that I can buy:"
						+ (Double) parameter.get("money") * 0.1 / stockPrices.get("TLT") + "\nIEF 10%:"
						+ (Double) parameter.get("money") * 0.1 + ", the number of stocks that I can buy:"
						+ (Double) parameter.get("money") * 0.1 / stockPrices.get("IEF") + "\nGLDM 5%:"
						+ (Double) parameter.get("money") * 0.05 + ", the number of stocks that I can buy:"
						+ (Double) parameter.get("money") * 0.05 / stockPrices.get("GLDM") + "\nQQQ 25%:"
						+ (Double) parameter.get("money") * 0.25 + ", the number of stocks that I can buy:"
						+ (Double) parameter.get("money") * 0.25 / stockPrices.get("QQQ") + "\nARKK 20%:"
						+ (Double) parameter.get("money") * 0.20 + ", the number of stocks that I can buy:"
						+ (Double) parameter.get("money") * 0.20 / stockPrices.get("ARKK") + "\nIBB 15%:"
						+ (Double) parameter.get("money") * 0.15 + ", the number of stocks that I can buy:"
						+ (Double) parameter.get("money") * 0.15 / stockPrices.get("IBB") + "\nRest:"
						+ ((Double) parameter.get("money") * 0.15
								+ getMoneyAfterBuy((Double) parameter.get("money"), 0.1, "TLT")
								+ getMoneyAfterBuy((Double) parameter.get("money"), 0.1, "IEF")
								+ getMoneyAfterBuy((Double) parameter.get("money"), 0.05, "GLDM")
								+ getMoneyAfterBuy((Double) parameter.get("money"), 0.25, "QQQ")
								+ getMoneyAfterBuy((Double) parameter.get("money"), 0.2, "ARKK")
								+ getMoneyAfterBuy((Double) parameter.get("money"), 0.15, "IBB")));
				break;

			case "s":
			case "S":
				System.out.println("\n [Option1] TLT 15%:" + (Double) parameter.get("money") * 0.15
						+ ", the number of stocks that I can buy:"
						+ (Double) parameter.get("money") * 0.15 / stockPrices.get("TLT") + "\nIEF 15%:"
						+ (Double) parameter.get("money") * 0.15 + ", the number of stocks that I can buy:"
						+ (Double) parameter.get("money") * 0.15 / stockPrices.get("IEF") + "\nGLDM 10%:"
						+ (Double) parameter.get("money") * 0.1 + ", the number of stocks that I can buy:"
						+ (Double) parameter.get("money") * 0.1 / stockPrices.get("GLDM") + "\nQQQ 15%:"
						+ (Double) parameter.get("money") * 0.15 + ", the number of stocks that I can buy:"
						+ (Double) parameter.get("money") * 0.15 / stockPrices.get("QQQ") + "\nARKK 15%:"
						+ (Double) parameter.get("money") * 0.15 + ", the number of stocks that I can buy:"
						+ (Double) parameter.get("money") * 0.15 / stockPrices.get("ARKK") + "\nIBB 15%:"
						+ (Double) parameter.get("money") * 0.15 + ", the number of stocks that I can buy:"
						+ (Double) parameter.get("money") * 0.15 / stockPrices.get("IBB") + "\nRest:"
						+ ((Double) parameter.get("money") * 0.15
								+ getMoneyAfterBuy((Double) parameter.get("money"), 0.15, "TLT")
								+ getMoneyAfterBuy((Double) parameter.get("money"), 0.15, "IEF")
								+ getMoneyAfterBuy((Double) parameter.get("money"), 0.1, "GLDM")
								+ getMoneyAfterBuy((Double) parameter.get("money"), 0.15, "QQQ")
								+ getMoneyAfterBuy((Double) parameter.get("money"), 0.15, "ARKK")
								+ getMoneyAfterBuy((Double) parameter.get("money"), 0.15, "IBB")));
				System.out.println("\n [Option2] TLT 15%:" + (Double) parameter.get("money") * 0.15
						+ ", the number of stocks that I can buy:"
						+ (Double) parameter.get("money") * 0.15 / stockPrices.get("TLT") + "\nIEF 15%:"
						+ (Double) parameter.get("money") * 0.15 + ", the number of stocks that I can buy:"
						+ (Double) parameter.get("money") * 0.15 / stockPrices.get("IEF") + "\nGLDM 10%:"
						+ (Double) parameter.get("money") * 0.1 + ", the number of stocks that I can buy:"
						+ (Double) parameter.get("money") * 0.1 / stockPrices.get("GLDM") + "\nQQQ 10%:"
						+ (Double) parameter.get("money") * 0.10 + ", the number of stocks that I can buy:"
						+ (Double) parameter.get("money") * 0.10 / stockPrices.get("QQQ") + "\nARKK 10%:"
						+ (Double) parameter.get("money") * 0.10 + ", the number of stocks that I can buy:"
						+ (Double) parameter.get("money") * 0.10 / stockPrices.get("ARKK") + "\nIBB 10%:"
						+ (Double) parameter.get("money") * 0.10 + ", the number of stocks that I can buy:"
						+ (Double) parameter.get("money") * 0.10 / stockPrices.get("IBB") + "\nRest:"
						+ ((Double) parameter.get("money") * 0.3
								+ getMoneyAfterBuy((Double) parameter.get("money"), 0.15, "TLT")
								+ getMoneyAfterBuy((Double) parameter.get("money"), 0.15, "IEF")
								+ getMoneyAfterBuy((Double) parameter.get("money"), 0.1, "GLDM")
								+ getMoneyAfterBuy((Double) parameter.get("money"), 0.10, "QQQ")
								+ getMoneyAfterBuy((Double) parameter.get("money"), 0.10, "ARKK")
								+ getMoneyAfterBuy((Double) parameter.get("money"), 0.10, "IBB")));
				break;

			}
			System.out.println("\nNVDA price: " + stockPrices.get("NVDA") + "\nMRK price: " + stockPrices.get("MRK")
					+ "\nGILD price: " + stockPrices.get("GILD"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static Double getPrice(String stockSymbol) {
		List<HistoricalQuote> stockHistory = null;
		try {
			Stock stock = stocks.get(stockSymbol);
			stockHistory = stock.getHistory(Calendar.getInstance());
		} catch (IOException e) {
			e.printStackTrace();
		}
		Matcher m = p.matcher(stockHistory.toString());
		m.find();
		return Double.parseDouble(m.group(1));
	}

	private static Double getMoneyAfterBuy(Double myMoney, Double percentage, String stockSymbol) {
		return (myMoney * percentage
				- ((int) ((myMoney * percentage) / stockPrices.get(stockSymbol))) * stockPrices.get(stockSymbol));
	}

	private static void handleCommand(String[] args, HashMap<String, Object> parameter) {
		Options options = new Options();
		Option preference = new Option("p", "preference", true,
				"investment preference(a:aggressive, n:neutral, s:safe)");
		Option money = new Option("m", "money", true, "the amount of money that you want to invest");
		preference.setRequired(true);
		money.setRequired(true);
		options.addOption(preference);
		options.addOption(money);
		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();
		CommandLine cmd;
		try {
			cmd = parser.parse(options, args);
			if (cmd.getOptionValue("preference").matches("[aAnNsS]")) {
				parameter.put("preference", cmd.getOptionValue("preference"));
			} else {
				throw new ParseException("");
			}
			parameter.put("money", Double.parseDouble(cmd.getOptionValue("money")));
		} catch (ParseException | NumberFormatException e) {
			System.out.println(e.getMessage());
			formatter.printHelp(" ", options);
			System.exit(1);
		}
	}
}
