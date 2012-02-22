package org.slc.sli.api.security.saml2;

import java.security.Key;
import java.security.KeyException;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.List;

import javax.xml.crypto.AlgorithmMethod;
import javax.xml.crypto.KeySelector;
import javax.xml.crypto.KeySelectorException;
import javax.xml.crypto.KeySelectorResult;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import javax.xml.crypto.dsig.keyinfo.X509Data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 * A basic implementation of a SAML2 validator. This is based on OpenAM
 * as it was configured on 2/16/2012.
 */
@Component
public class DefaultSAML2Validator implements SAML2Validator {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultSAML2Validator.class);
    
    private DOMValidateContext valContext;
    
    private NodeList getSignatureElement(Document samlDocument) {
        return samlDocument.getElementsByTagNameNS(XMLSignature.XMLNS, "Signature");
    }
    
    private void createContext(Document samlDocument) {
        this.valContext = new DOMValidateContext(new KeyValueKeySelector(), getSignatureElement(samlDocument).item(0));
    }
    
    private XMLSignature getSignature(Document samlDocument) throws MarshalException {
        createContext(samlDocument);
        XMLSignatureFactory factory = XMLSignatureFactory.getInstance("DOM");
        return factory.unmarshalXMLSignature(valContext);
    }
    
    @Override
    public boolean isDocumentValid(Document samlDocument) {
        try {
            return getSignature(samlDocument).validate(valContext);
        } catch (MarshalException e) {
            LOG.warn("Couldn't validate Document", e);
            return false;
        } catch (XMLSignatureException e) {
            LOG.warn("Couldn't validate Document", e);
            return false;
        }
    }
    
    @Override
    public boolean isSignatureValid(Document samlDocument) {
        try {
            return getSignature(samlDocument).getSignatureValue().validate(valContext);
        } catch (MarshalException e) {
            LOG.warn("Couldn't validate signature", e);
            return false;
        } catch (XMLSignatureException e) {
            LOG.warn("Couldn't validate signature", e);
            return false;
        }
        
    }
    
    @Override
    public boolean isDigestValid(Document samlDocument) {
        boolean valid = false;
        try {
            Iterator iterator = getSignature(samlDocument).getSignedInfo().getReferences().iterator();
            for (; iterator.hasNext();) {
                Reference ref = ((Reference) iterator.next());
                valid = ref.validate(valContext);
            }
        } catch (XMLSignatureException e) {
            LOG.warn("Couldn't validate digest", e);
            return false;
        } catch (MarshalException e) {
            LOG.warn("Couldn't validate digest", e);
            return false;
        }
        return valid;
    }
    
    @Override
    public Document signDocumentWithSAMLSigner(Document samlDocument, SAML2Signer signer) {
        return null;
    }
    
    private static class KeyValueKeySelector extends KeySelector {
        
        public KeySelectorResult select(KeyInfo keyInfo, KeySelector.Purpose purpose, AlgorithmMethod method, XMLCryptoContext context) throws KeySelectorException {
            
            if (keyInfo == null) {
                throw new KeySelectorException("Null KeyInfo object!");
            }
            SignatureMethod sm = (SignatureMethod) method;
            List list = keyInfo.getContent();
            
            for (Object struct : list) {
                XMLStructure xmlStructure = (XMLStructure) struct;
                if (xmlStructure instanceof KeyValue) {
                    PublicKey pk = null;
                    try {
                        pk = ((KeyValue) xmlStructure).getPublicKey();
                    } catch (KeyException ke) {
                        throw new KeySelectorException(ke);
                    }
                    // make sure algorithm is compatible with method
                    if (algEquals(sm.getAlgorithm(), pk.getAlgorithm())) {
                        return new SimpleKeySelectorResult(pk);
                    }
                }
                if (xmlStructure instanceof X509Data) {
                    X509Data xd = (X509Data) xmlStructure;
                    Iterator data = xd.getContent().iterator();
                    for (; data.hasNext();) {
                        Object o = data.next();
                        if (o instanceof X509Certificate) {
                            X509Certificate cert = (X509Certificate) o;
                            return new SimpleKeySelectorResult(cert.getPublicKey());
                        }
                    }
                }
            }
            throw new KeySelectorException("No KeyValue element found!");
        }
        
        static boolean algEquals(String algURI, String algName) {
            return (algName.equalsIgnoreCase("DSA") && algURI.equalsIgnoreCase(SignatureMethod.DSA_SHA1)) || (algName.equalsIgnoreCase("RSA") && algURI.equalsIgnoreCase(SignatureMethod.RSA_SHA1));
        }
        
        public class SimpleKeySelectorResult implements KeySelectorResult {
            private Key k;
            
            public SimpleKeySelectorResult(PublicKey k) {
                this.k = k;
            }
            
            @Override
            public Key getKey() {
                return k;
            }
        }
    }
}
