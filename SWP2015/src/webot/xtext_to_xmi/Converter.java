package webot.xtext_to_xmi;

import java.io.IOException;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.xtext.bot.language.BlaStandaloneSetupGenerated;
import org.xtext.bot.language.bla.impl.BlaPackageImpl;
import com.google.inject.Injector;

/**
 * This program takes a DSL text file and converts it to xmi
 */
public class Converter {
	/**
	 * Given Converter!
	 * 
	 * @param dnitxtFile
	 * @param xmiFile
	 */
	public void convert_to_xmi(final String dnitxtFile, final String xmiFile) {
		BlaStandaloneSetupGenerated _blaStandaloneSetupGenerated = new BlaStandaloneSetupGenerated();
		final Injector injector = _blaStandaloneSetupGenerated.createInjectorAndDoEMFRegistration();
		BlaPackageImpl.init();

		final XtextResourceSet resourceSet = injector.<XtextResourceSet> getInstance(XtextResourceSet.class);
		final URI uri = URI.createURI(dnitxtFile);
		final Resource xtextResource = resourceSet.getResource(uri, true);

		EcoreUtil.resolveAll(xtextResource);

		URI _createURI = URI.createURI(xmiFile);
		final Resource xmiResource = resourceSet.createResource(_createURI);

		EList<EObject> _contents = xmiResource.getContents();
		EList<EObject> _contents_1 = xtextResource.getContents();

		EObject _get = _contents_1.get(0);
		_contents.add(_get);

		try {
			xmiResource.save(null);
		} catch (final Throwable _t) {
			if (_t instanceof IOException) {
				final IOException e = (IOException) _t;
				e.printStackTrace();
			} else {
				throw Exceptions.sneakyThrow(_t);
			}
		}
	}
}
