package webot.logic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeoutException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.openqa.selenium.NoSuchElementException;
import org.xtext.bot.language.bla.Addition;
import org.xtext.bot.language.bla.Address;
import org.xtext.bot.language.bla.Block;
import org.xtext.bot.language.bla.Click;
import org.xtext.bot.language.bla.CompareFeature;
import org.xtext.bot.language.bla.Connect;
import org.xtext.bot.language.bla.Division;
import org.xtext.bot.language.bla.IFStatement;
import org.xtext.bot.language.bla.IfCompareExpression;
import org.xtext.bot.language.bla.MathValue;
import org.xtext.bot.language.bla.MathematicalInstructions;
import org.xtext.bot.language.bla.Multiplication;
import org.xtext.bot.language.bla.NoClose;
import org.xtext.bot.language.bla.Notification;
import org.xtext.bot.language.bla.Read;
import org.xtext.bot.language.bla.Show;
import org.xtext.bot.language.bla.Start;
import org.xtext.bot.language.bla.Stop;
import org.xtext.bot.language.bla.Subtraction;
import org.xtext.bot.language.bla.Var;
import org.xtext.bot.language.bla.Wait;
import org.xtext.bot.language.bla.WhileLoop;
import org.xtext.bot.language.bla.Write;
import org.xtext.bot.language.bla.impl.AddressImpl;
import org.xtext.bot.language.bla.impl.CharSeqImpl;
import org.xtext.bot.language.bla.impl.ClickImpl;
import org.xtext.bot.language.bla.impl.ConnectImpl;
import org.xtext.bot.language.bla.impl.IFStatementImpl;
import org.xtext.bot.language.bla.impl.IfAndExpressionImpl;
import org.xtext.bot.language.bla.impl.IfCompareExpressionImpl;
import org.xtext.bot.language.bla.impl.IfOrExpressionImpl;
import org.xtext.bot.language.bla.impl.NotificationImpl;
import org.xtext.bot.language.bla.impl.ReadImpl;
import org.xtext.bot.language.bla.impl.ShowImpl;
import org.xtext.bot.language.bla.impl.StartImpl;
import org.xtext.bot.language.bla.impl.VarImpl;
import org.xtext.bot.language.bla.impl.WaitImpl;
import org.xtext.bot.language.bla.impl.WriteImpl;

import webot.WeBot;
import webot.logs.Log;
import webot.watchValue.WatchValue;

/**
 * 
 * Class for parsing the XMI file data into java
 *
 */
public class LogicExecutor {
	private GameLogic gameLogic;
	private boolean stopped = false;
	private WeBot weBot;

	/**
	 * Constructor
	 */
	public LogicExecutor(WeBot weBot) {
		this.gameLogic = new GameLogic();
		this.weBot = weBot;
	}

	/**
	 * Executing the Logic given from the user
	 * 
	 * @param list
	 *            : list including the EObjects generated by the specified Xtext
	 *            Logic ??
	 */
	public void executeLogic(EList<EObject> list, String browser, String path) {

		// going through EList and executing instructions
		for (EObject instruction : list) {

			if (stopped) {
				log("Stopped!");
				gameLogic.stop();
				weBot.getStopButton().fire();
				weBot.setStatus("Stopped!");
				stopped = true;
				break;
			}
			stopped = weBot.isStoped();

			if (instruction instanceof Start) {
				String start = ((StartImpl) instruction).getName();

				Date date = null;

				SimpleDateFormat sdf = new SimpleDateFormat();

				// try to find a user defined start date(time)
				try {
					date = sdf.parse(start);
				}
				// if unable to find a valid date, set time to the current and
				// start actions
				catch (ParseException e) {
					date = new Date(System.currentTimeMillis());
				}

				log("Starting at " + date);
				try {
					gameLogic.start(date, browser, path);
				} catch (Exception e) {
					log("Error while initializing Browser");
					gameLogic.stop();
					weBot.getStopButton().fire();
					weBot.setStatus("Stopped!");
					break;
				}
			}

			if (instruction instanceof Notification) {
				String massage = ((NotificationImpl) instruction).getName();

				try {
					log(gameLogic.notifyMessage(massage));
				} catch (AddressException e) {
					log("ERROR: Invalid email address!");
				} catch (MessagingException e) {
					log("ERROR: Email not sent!");
				}
			}

			if (instruction instanceof Address) {
				String address = ((AddressImpl) instruction).getName();
				gameLogic.setAddress(address);
				log("Email address for notification!");
			}

			if (instruction instanceof Stop) {
				stopped = true;
				gameLogic.stop();
				log("Stopped by script!");
				weBot.getStopButton().fire();
				weBot.setStatus("Stopped!");
				break;
			}

			if (instruction instanceof NoClose) {
				gameLogic.noClose();
				log("Browser will not close!");
			}

			if (instruction instanceof Wait) {
				log("Waiting...");

				int millis = ((WaitImpl) instruction).getName();
				gameLogic.executeWait(millis);

				log("Continue...");
			}

			if (instruction instanceof Show) {
				EList<String> names = ((ShowImpl) instruction).getName();
				EList<String> values = ((ShowImpl) instruction).getValue();

				try {
					List<WatchValue> watchValues;
					watchValues = gameLogic.showStatistc(names, values);
					weBot.setWatchValues(watchValues);

					log("Showing stats!");
				} catch (NoSuchElementException e) {
					stopped = true;
					log(e.getMessage());
					try {
						gameLogic.notifyMessage(e.getMessage());
					} catch (Exception f) {
						stopped = true;
						log(f.getMessage());
					}
					stopped = true;
				} catch (TimeoutException e) {
					stopped = true;
					log(e.getMessage());
					try {
						gameLogic.notifyMessage(e.getMessage());
					} catch (Exception f) {
						log(f.getMessage());
						stopped = true;
					}
					stopped = true;
				}
			}

			if (instruction instanceof Connect) {
				String url = ((ConnectImpl) instruction).getName();
				log("Try to connect to the following URL: " + url);
				gameLogic.connect(url);
			}

			if (instruction instanceof Write) {
				String xpath = ((WriteImpl) instruction).getValue();
				String value = ((WriteImpl) instruction).getName();

				try {
					gameLogic.write(xpath, value);
					log("Wrote the following value: " + value + "  to the following xpath: " + xpath);
				} catch (NoSuchElementException e) {
					stopped = true;
					log(e.getMessage());
					try {
						gameLogic.notifyMessage(e.getMessage());
					} catch (Exception f) {
						stopped = true;
						log(f.getMessage());
					}
					stopped = true;
				} catch (TimeoutException e) {
					stopped = true;
					log(e.getMessage());
					try {
						gameLogic.notifyMessage(e.getMessage());
					} catch (Exception f) {
						stopped = true;
						log(f.getMessage());
					}
					stopped = true;
				}
			}

			if (instruction instanceof Read) {
				String xpath = ((ReadImpl) instruction).getName();
				String result = "";

				try {
					result = gameLogic.read(xpath);
					log("Read the following value " + result + " from the following xpath:" + xpath);
				} catch (NoSuchElementException e) {
					stopped = true;
					log(e.getMessage());
					try {
						gameLogic.notifyMessage(e.getMessage());
					} catch (Exception f) {
						stopped = true;
						log(f.getMessage());
					}
					stopped = true;
				} catch (TimeoutException e) {
					log(e.getMessage());
					try {
						stopped = true;
						gameLogic.notifyMessage(e.getMessage());
					} catch (Exception f) {
						stopped = true;
						log(f.getMessage());
					}
					stopped = true;
				}

			}

			if (instruction instanceof Click) {
				String xpath = ((ClickImpl) instruction).getName();

				try {
					gameLogic.click(xpath);
					log("Clicked at the following xpath: " + xpath);

				} catch (NoSuchElementException e) {
					stopped = true;
					log(e.getMessage());
					try {
						gameLogic.notifyMessage(e.getMessage());
					} catch (Exception f) {
						stopped = true;
						log(f.getMessage());
					}
					stopped = true;
				} catch (TimeoutException e) {
					stopped = true;
					log(e.getMessage());
					try {
						gameLogic.notifyMessage(e.getMessage());
					} catch (Exception f) {
						stopped = true;
						log(f.getMessage());
					}
					stopped = true;
				}
			}

			if (instruction instanceof Var) {
				String varName = ((VarImpl) instruction).getName();
				String value = "";

				try {
					value = gameLogic.read(((VarImpl) instruction).getValue().getName());
					gameLogic.newVariable(varName, value);

					log("Set the following variable name " + varName + " with the specified read xpath-value:" + value);

				} catch (NoSuchElementException e) {
					stopped = true;
					log(e.getMessage());
					try {
						gameLogic.notifyMessage(e.getMessage());
					} catch (Exception f) {
						stopped = true;
						log(f.getMessage());
					}
					stopped = true;
				} catch (TimeoutException e) {
					stopped = true;
					log(e.getMessage());
					try {
						gameLogic.notifyMessage(e.getMessage());
					} catch (Exception f) {
						stopped = true;
						log(f.getMessage());
					}
					stopped = true;
				}

			}

			if (instruction instanceof IFStatement) {
				log("If-Statement recognized!");

				if (rateCondition(((IFStatementImpl) instruction).getIf())) {
					EList<Block> thenList = ((IFStatementImpl) instruction).getThen();
					this.executeLogic(blockListToEObjectList(thenList), browser, path);
					log("Then block executed!");
				} else {
					EList<Block> elseList = ((IFStatementImpl) instruction).getElse();

					if (elseList != null) {
						this.executeLogic(blockListToEObjectList(elseList), browser, path);
						log("Else block executed!");
					}
				}

			}

			if (instruction instanceof WhileLoop) {
				log("While loop recognized!");
				while (rateCondition(((WhileLoop) instruction).getWhile())) {
					EList<Block> doLoopList = ((WhileLoop) instruction).getDoLoop();
					this.executeLogic(blockListToEObjectList(doLoopList), browser, path);
					log("Loop completed!");
				}
			}
		}
	}

	/**
	 * 
	 * @param originalList
	 * @return
	 */
	private EList<EObject> blockListToEObjectList(EList<Block> originalList) {
		EList<EObject> list = new BasicEList<EObject>();

		for (Block block : originalList) {
			list.add(block.getAction());
		}

		return list;
	}

	/**
	 * Function to extract all needed operators to decide the boolean value of
	 * an If-Statement
	 * 
	 * @param ifStatement
	 * @return
	 */
	private boolean rateCondition(EObject ifStatement) {
		if (ifStatement.eClass().getName().equals("IfCompareExpression")) {
			CompareFeature left = ((IfCompareExpressionImpl) ifStatement).getLeftFeature();
			CompareFeature right = ((IfCompareExpressionImpl) ifStatement).getRightFeature();
			String operant = ((IfCompareExpressionImpl) ifStatement).getCompareOperant();

			String leftValue = getComponentAsString(left);
			String rightValue = getComponentAsString(right);

			return gameLogic.rateIf(leftValue, rightValue, operant);

		}

		if (ifStatement.eClass().getName().equals("IfAndExpression")) {
			IfCompareExpression left = ((IfAndExpressionImpl) ifStatement).getLeftFeature();
			EObject right = ((IfAndExpressionImpl) ifStatement).getRightFeature();

			return rateCondition(left) & rateCondition(right);
		}

		if (ifStatement.eClass().getName().equals("IfOrExpression")) {
			IfCompareExpression left = ((IfOrExpressionImpl) ifStatement).getLeftFeature();
			EObject right = ((IfOrExpressionImpl) ifStatement).getRightFeature();

			return rateCondition(left) | rateCondition(right);
		}

		return false;

	}

	/**
	 * 
	 * @param term
	 * @return
	 */
	private double getNumberIn(MathValue term) {
		if (term instanceof Read) {
			String xpath = ((Read) term).getName();
			String value = "";

			try {
				value = gameLogic.read(xpath);
			} catch (NoSuchElementException e) {
				stopped = true;
				log(e.getMessage());
				try {
					gameLogic.notifyMessage("ERROR: " + e.getMessage());
				} catch (Exception f) {
					stopped = true;
					log(f.getMessage());
				}
				stopped = true;
			} catch (TimeoutException e) {
				stopped = true;
				log(e.getMessage());
				try {
					gameLogic.notifyMessage("ERROR: " + e.getMessage());
				} catch (Exception f) {
					stopped = true;
					log(f.getMessage());
				}
				stopped = true;
			}

			return Double.parseDouble(value);
		}

		if (term instanceof Var) {
			String varName = ((Var) term).getName();
			String value = gameLogic.readVariable(varName);
			return Double.parseDouble(value);
		}

		return 0;
	}

	/**
	 * 
	 * @param feature
	 * @return
	 */
	private String getComponentAsString(CompareFeature feature) {
		String result = null;

		if (feature instanceof CharSeqImpl) {
			result = ((CharSeqImpl) feature).getValue();
		}

		else if (feature instanceof Read) {
			String xpath = ((Read) feature).getName();

			try {
				result = gameLogic.read(xpath);
			} catch (NoSuchElementException e) {
				stopped = true;
				log(e.getMessage());
				try {
					gameLogic.notifyMessage("ERROR: " + e.getMessage());
				} catch (Exception f) {
					stopped = true;
					log(f.getMessage());
				}
				stopped = true;
			} catch (TimeoutException e) {
				stopped = true;
				log(e.getMessage());
				try {
					stopped = true;
					gameLogic.notifyMessage("ERROR: " + e.getMessage());
				} catch (Exception f) {
					stopped = true;
					log(f.getMessage());
				}
				stopped = true;
			}
		}

		else if (feature instanceof MathematicalInstructions) {
			result = null;
		}

		if (feature instanceof Addition) {
			double termLeft = getNumberIn(((Addition) feature).getValueLeft());
			double termRight = getNumberIn(((Addition) feature).getValueRight());
			return Double.toString(termLeft + termRight);
		}
		if (feature instanceof Subtraction) {
			double termLeft = getNumberIn(((Subtraction) feature).getValueLeft());
			double termRight = getNumberIn(((Subtraction) feature).getValueRight());
			return Double.toString(termLeft - termRight);
		}
		if (feature instanceof Multiplication) {
			double termLeft = getNumberIn(((Multiplication) feature).getValueLeft());
			double termRight = getNumberIn(((Multiplication) feature).getValueRight());
			return Double.toString(termLeft * termRight);
		}
		if (feature instanceof Division) {
			double termLeft = getNumberIn(((Division) feature).getValueLeft());
			double termRight = getNumberIn(((Division) feature).getValueRight());
			return Double.toString(termLeft / termRight);
		}

		else {
			if (feature.getVariableFeature() instanceof Var) {
				String name = feature.getVariableFeature().getName();
				result = gameLogic.readVariable(name);
			}
		}

		return result;
	}

	/**
	 * Abording the Application
	 */
	public void abort() {
		stopped = true;
	}

	/**
	 * Release the log messages for all different user inputs (like wait,
	 * connect, read ,..)
	 * 
	 * @param messages
	 * 
	 */
	public void log(String message) {
		Log log = new Log(message);
		weBot.addLog(log);
	}

	public WeBot getWebot() {
		return this.weBot;
	}
}
