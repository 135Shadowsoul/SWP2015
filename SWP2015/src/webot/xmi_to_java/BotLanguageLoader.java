package webot.xmi_to_java;

import java.io.FileInputStream;
import java.util.Collections;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.xtext.bot.language.BlaStandaloneSetupGenerated;
import org.xtext.bot.language.bla.impl.BotLanguageImpl;

@SuppressWarnings("all")
public class BotLanguageLoader
{
	
//  public static void main(final String[] args) 
//  {
//    BotLanguageLoader _botLanguageLoader = new BotLanguageLoader();
////    _botLanguageLoader.loadBotLanguage();
//  }
  
  
	/**
	 * Load the xmi file into the EList
	 * 
	 * @param xmiFile : Delivered String xmiFile from the Converter
	 * @return : Returns an EList over EObjects -- Used to parse out the user input
	 */
  public EList<EObject> loadBotLanguage(String xmiFile)
  {
    try 
    {
      BlaStandaloneSetupGenerated _blaStandaloneSetupGenerated = new BlaStandaloneSetupGenerated();
      _blaStandaloneSetupGenerated.createInjectorAndDoEMFRegistration();
      
      final FileInputStream fileName = new FileInputStream(xmiFile);
      XMIResourceImpl _xMIResourceImpl = new XMIResourceImpl();
      
      final Procedure1<XMIResourceImpl> _function = new Procedure1<XMIResourceImpl>() 
      {
        public void apply(final XMIResourceImpl it)
        {
          try 
          {
            it.load(fileName, Collections.EMPTY_MAP);
          } 
          catch (Throwable _e) 
          {
            throw Exceptions.sneakyThrow(_e);
          }
        }
      };
      
      XMIResourceImpl xmiResLoad = ObjectExtensions.<XMIResourceImpl>operator_doubleArrow(_xMIResourceImpl, _function);
      EList<EObject> _contents = xmiResLoad.getContents();
      EObject _get = _contents.get(0);
      
      BotLanguageImpl bli = ((BotLanguageImpl) _get);
      EList<EObject> instr = bli.eContents();
      
      return instr;  
    }
    catch (Throwable _e)
    {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
