#include "com_yjn_text_JniClient.h"
#include <stdlib.h>
#include <stdio.h>
#include <yjn.h>
#include <ImageUtil.h>
#ifdef __cplusplus 
extern "C" 
{ 
#endif 
/*
* Class: com_ndk_test_JniClient
* Method: AddStr
* Signature: (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
*/

JNIEXPORT jintArray JNICALL Java_com_yjn_text_JniClient_getFasetBlurPix
  (JNIEnv *env, jclass ara, jintArray pixs, jint w, jint h, jint radius){
	return stackBlur(pixs , w , h , radius);
}
#ifdef __cplusplus 
} 
#endif
