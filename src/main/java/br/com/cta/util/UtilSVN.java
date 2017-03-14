/*
 * @(#)MetodosSVN.java
 *
 * Copyright 2012, Drive Consultoria e Inform�tica Ltda.
 * Rua da Alf�ndega, 91 andar 9
 * Rio de Janeiro - RJ - 20070-003
 * BRASIL
 *
 * Todos os direitos reservados.
 */
 
package br.com.cta.util;

import java.io.File;

import org.springframework.stereotype.Component;
import org.tigris.subversion.javahl.DirEntry;
import org.tigris.subversion.javahl.Revision;
import org.tmatesoft.svn.core.SVNCancelException;
import org.tmatesoft.svn.core.SVNCommitInfo;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNErrorMessage;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNProperties;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.javahl.SVNClientImpl;
import org.tmatesoft.svn.core.wc.ISVNEventHandler;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNCopySource;
import org.tmatesoft.svn.core.wc.SVNEvent;
import org.tmatesoft.svn.core.wc.SVNEventAction;
import org.tmatesoft.svn.core.wc.SVNInfo;
import org.tmatesoft.svn.core.wc.SVNRevision;

/**
 * Classe <code>UtilSVN</code>.
 */
@Component
public class UtilSVN
{
    public static SVNClientManager s_svnClientManager = SVNClientManager.newInstance( );
    
    /**
     *
     * @param aobjs
     * @param strBloqueioMsg
     * @return
     * @throws Exception
     */
    public static String bloqueiaObjetos( Object[] aobjs, String strBloqueioMsg )
    throws Exception
    {
        return bloqueiaDesbloqueiaObjetos( true, aobjs, strBloqueioMsg );
    }
    
    /**
     *
     * @param aobjs
     * @return
     * @throws Exception
     */
    public static String desbloqueiaObjetos( Object[] aobjs )
    throws Exception
    {
        return bloqueiaDesbloqueiaObjetos( false, aobjs, null );
    }
    
    /**
     *
     * @param bBloqueia
     * @param aobjs
     * @param strMsg
     * @return
     * @throws Exception
     */
    private static String bloqueiaDesbloqueiaObjetos( final boolean bBloqueia, Object[] aobjs, String strMsg )
    throws Exception
    {
        String strMsgErro = "";
        
        s_svnClientManager.setEventHandler( new ISVNEventHandler( ) 
        {
            public void handleEvent( SVNEvent event, double progress ) throws SVNException 
            {
                if( event.getAction( ) == ( bBloqueia ? SVNEventAction.LOCK_FAILED : SVNEventAction.UNLOCK_FAILED ) )
                {
                    SVNErrorMessage err = event.getErrorMessage( ); 
                    throw new SVNException( err );
                }
            }
            public void checkCancelled( ) throws SVNCancelException { }
        });
        
        try 
        {
            if( bBloqueia )
            {
                if( aobjs instanceof SVNURL[] )
                    s_svnClientManager.getWCClient( ).doLock( (SVNURL[])aobjs, false, strMsg );
                
                if( aobjs instanceof File[] )
                    s_svnClientManager.getWCClient( ).doLock(   (File[])aobjs, false, strMsg );
            }
            else
            {
                if( aobjs instanceof SVNURL[] )
                    s_svnClientManager.getWCClient( ).doUnlock( (SVNURL[])aobjs, true );
                
                if( aobjs instanceof File[] )
                    s_svnClientManager.getWCClient( ).doUnlock(   (File[])aobjs, true );
            }
        } 
        catch( SVNException e )
        {
            strMsgErro = e.getErrorMessage( ).getFullMessage( );
        }
        
        return strMsgErro;
    }
    
    /**
     *
     * @param strURL
     * @return
     * @throws Exception
     */
    public static SVNRevision coletaRevisionHEAD( String strURL )
    throws Exception
    {
        return coletaInfo( strURL, SVNRevision.HEAD ).getRevision( );
    }
    
    /**
     * @param strURL a URL of an item which information is to be obtained
     * @param rev a target revision
     * @return collected info
     * @throws Exception
     */
    public static SVNInfo coletaInfo( String strURL, SVNRevision rev )
    throws Exception
    {
        SVNInfo info = null;
        
        info = s_svnClientManager.getWCClient( ).doInfo( SVNURL.parseURIEncoded( strURL ), SVNRevision.UNDEFINED, rev );
        
        return info;
    }
    
    /**
    *
    * @param strURL
    * @return
    * @throws Exception
    */
    public static boolean isnExisteURL( String strURL )
    throws Exception
    {
        try
        {
            UtilSVN.coletaInfo( strURL, SVNRevision.HEAD ); 
        }
        catch( Exception e )
        {
            return false;
        }
       
        return true;
    }
    
    /**
     *
     * @param strURL
     * @param strPathDest
     * @param rev
     * @return
     * @throws Exception
     */
    public static Long exportaArq( String strURL, String strPathDest, SVNRevision rev )
    throws Exception
    {
        long lRev = 0L;

        File fileDest = new File( strPathDest );
        
        lRev = s_svnClientManager.getUpdateClient( ).doExport( 
            SVNURL.parseURIEncoded( strURL ), fileDest, SVNRevision.UNDEFINED, 
            rev, null, true, SVNDepth.FILES );

        return lRev;
    }
    
    /**
    *
    * @param strURL
    * @param strPathDest
    * @param rev
    * @return
    * @throws Exception
    */
   public static Long exportaDir( String strURL, final String strPathDest, SVNRevision rev )
   throws Exception
   {
       long lRev = 0L;

       File fileDest = new File( strPathDest );
       
       UtilSVN.s_svnClientManager.getUpdateClient( ).setEventHandler( new ISVNEventHandler( ) {
           public void handleEvent( SVNEvent event, double progress ) 
           {
               System.out.println( event.toString( ).replace( strPathDest, "..." ) );
           }
          
           public void checkCancelled( ) 
           throws SVNCancelException 
           {
               //handle cancel of the operation - throw SVNCancelException  
           }
       });
       
       lRev = s_svnClientManager.getUpdateClient( ).doExport( 
           SVNURL.parseURIEncoded( strURL ), fileDest, SVNRevision.UNDEFINED, 
           rev, null, true, SVNDepth.INFINITY );

       return lRev;
   }
    
    /**
     *
     * @param strURL
     * @return
     * @throws Exception
     */
    public static DirEntry[] coletaDirEntries( String strURL )
    throws Exception
    {
        SVNClientImpl svn = SVNClientImpl.newInstance( );
        
        DirEntry[] aDirEntry = svn.list( strURL, Revision.HEAD, false );
        
        return aDirEntry;
    }
    
    /**
     *
     * @param strURL a repository location from where a Working Copy will be checked out
     * @param strPathDest the local path where the Working Copy will be placed
     * @param revision the desired revision of the Working Copy to be checked out
     * @return
     * @throws Exception
     */
    public static long checkout( String strURL, final String strPathDest, SVNRevision revision )
    throws Exception
    {
        return checkout( strURL, strPathDest, revision, SVNDepth.INFINITY );
    }
    
    /**
     *
     * @param strURL a repository location from where a Working Copy will be checked out
     * @param strPathDest the local path where the Working Copy will be placed
     * @param revision the desired revision of the Working Copy to be checked out
     * @return
     * @throws Exception
     */
    public static long checkout( String strURL, final String strPathDest, SVNRevision revision, SVNDepth svnDepth )
    throws Exception
    {
        long lRev = 0L;
       
        File file = new File( strPathDest );
       
        if( file.exists( ) && file.isDirectory( ) )
        {
            UtilSVN.s_svnClientManager.getUpdateClient( ).setEventHandler( new ISVNEventHandler( ) {
                public void handleEvent( SVNEvent event, double progress ) 
                {
                    System.out.println( event.toString( ).replace( strPathDest, "..." ) );
                }
               
                public void checkCancelled( ) 
                throws SVNCancelException 
                {
                    //handle cancel of the operation - throw SVNCancelException  
                }
            });
           
            lRev = s_svnClientManager.getUpdateClient( ).doCheckout( 
                SVNURL.parseURIEncoded( strURL ), file, SVNRevision.UNDEFINED, 
                revision, svnDepth, false );
        }
       
        return lRev;
    }

    /**
     *
     * @param aFile an array of local items which should be traversed to collect information on every changed item (one SVNCommitItem per 
     * each modified local item)
     * @param bKeepLocks if true and there are local items that were locked then these items will be left locked after traversing all of
     * them, otherwise the items will be unlocked
     * @param strCommitMessage
     * @return
     * @throws Exception
     */
    public static String commit( File[] aFile, boolean bKeepLocks, String strCommitMessage )
    throws Exception
    {
        SVNCommitInfo commitInfo = s_svnClientManager.getCommitClient( ).doCommit(
            aFile, bKeepLocks, strCommitMessage, null, null, false, false, SVNDepth.FILES );
        
        return commitInfo != null ? commitInfo.toString( ) : null;
    }
    
    /**
     *
     * @param strURLOrigem
     * @param strURLDestino
     * @param bMove
     * @param bMakeParents
     * @param strCommitMessage
     * @return
     * @throws SVNException
     */
    public static String copia( String strURLOrigem, String strURLDestino, boolean bMove, boolean bMakeParents, String strCommitMessage, 
        SVNProperties svnProperties, SVNRevision svnRevision )
    throws SVNException
    {
        String strCommitInfo = "";
        
        SVNURL urlOrigem = SVNURL.parseURIEncoded( strURLOrigem );
        SVNCopySource copySource = new SVNCopySource( SVNRevision.UNDEFINED, svnRevision, urlOrigem );
        strCommitInfo = s_svnClientManager.getCopyClient( ).doCopy( new SVNCopySource[] { copySource }, 
            SVNURL.parseURIEncoded( strURLDestino ), bMove, bMakeParents, true, 
            strCommitMessage, svnProperties ).toString( );
        
        return strCommitInfo;
    }
    
    /**
     *
     * @param strPath
     * @param strTargetURL
     * @param revision
     * @return lRev
     * @throws Exception
     */
    public static long switchWCTarget( String strPath, String strTargetURL, SVNRevision revision )
    throws Exception
    {
        long lRev = 0L;
        
        File file = new File( strPath );
        
        if( file.exists( ) && file.isDirectory( ) )
            lRev = s_svnClientManager.getUpdateClient( ).doSwitch( 
                file, SVNURL.parseURIEncoded( strTargetURL ), SVNRevision.UNDEFINED, 
                revision, SVNDepth.INFINITY, false, false );
        
        return lRev;
    }
    
    /**
     *
     * @param strURL
     * @param strCommitMessage
     * @return
     * @throws Exception
     */
    public static String removeURL( String strURL, String strCommitMessage )
    throws Exception
    {
        String strDeleteInfo = "";
        
        SVNCommitInfo svnDeleteInfo = s_svnClientManager.getCommitClient( ).doDelete( 
            new SVNURL[] { SVNURL.parseURIEncoded( strURL ) }, strCommitMessage );
        strDeleteInfo = svnDeleteInfo.toString( );
         
        return strDeleteInfo;
    }
    
    /**
     *
     * @param strPath
     * @throws Exception
     */
    public static void update( String strPath )
    throws Exception
    {
        s_svnClientManager.getUpdateClient( ).doUpdate( new File( strPath ), 
            SVNRevision.HEAD, SVNDepth.INFINITY, false, false );
    }

}
