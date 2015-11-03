package com.liferay.devhacks.service.impl;

import com.liferay.devhacks.service.base.NoteServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Digester;
import com.liferay.portal.kernel.util.DigesterUtil;
import com.liferay.portal.kernel.util.SortedArrayList;
import com.liferay.portal.security.ac.AccessControlled;
import com.liferay.util.portlet.PortletProps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The implementation of the note remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.devhacks.service.NoteService} interface.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author James Falkner
 * @see com.liferay.devhacks.service.base.NoteServiceBaseImpl
 * @see com.liferay.devhacks.service.NoteServiceUtil
 */
public class NoteServiceImpl extends NoteServiceBaseImpl {
    /*
     * NOTE FOR DEVELOPERS:
     *
     * Never reference this interface directly. Always use {@link com.liferay.devhacks.service.NoteServiceUtil} to access the note remote service.
     */
    @Override
//    @AccessControlled(guestAccessEnabled = true)
    public String echo(final String name, final String note, String signature) throws PortalException {

//        Map<String, String> args = new HashMap<String, String>() {{
//            put("name", name);
//            put("note", note);
//        }};
//
//        if (!isValidSignature(args, signature)) {
//            throw new PortalException("invalid signature");
//        }
//
        return "Hi " + name + ", you said: " + note;
    }

//    private static boolean isValidSignature(Map<String, String> args, String signature) {
//
//        String secret = PortletProps.get("liferay.devhacks.secret");
//
//        List<String> sortedArgs = new SortedArrayList<String>();
//        sortedArgs.addAll(args.keySet());
//
//        String preSig = secret;
//        for (String paramName : sortedArgs) {
//            preSig += (paramName + "=" + args.get(paramName));
//        }
//
//        String shaSig = DigesterUtil.digestHex(Digester.SHA_256, preSig);
//
//        return shaSig.equals(signature);
//
//    }
}

