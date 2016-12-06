//
// Created by seryozhka on 2.12.16.
//
//#include <jni.h>
//using namespace std;
//
//extern "C" {
//    JNIEXPORT jint JNICALL
//    Java_com_seryozhka_getnews_MainActivity_addictionNative(JNIEnv *env, jobject thiz, int a, int b){
//    return a + b;
//    }
//}


#include <jni.h>
#include <vector>
#include <string>

using namespace std;
extern "C" {
int find_occurences(string article, string key);

JNIEXPORT jint JNICALL
Java_com_seryozhka_getnews_MainActivity_sortNative(JNIEnv *env, jobject thiz, jobjectArray obj,
                                              jobject key) {
    string keyValue = string(env->GetStringUTFChars((jstring) key, 0));

    int stringCount = env->GetArrayLength(obj);
    vector<string> news;

    for (int j = 0; j < stringCount; ++j) {
        jstring objString = (jstring) (env->GetObjectArrayElement(obj, j));
        const char *rawString = env->GetStringUTFChars(objString, 0);
        string str(rawString);
        news.push_back(str);
    }

    int occurences = 0;
    int index = 0;
    for (int i = 0; i < news.size(); ++i) {
        string article = news[i];
        int occ = find_occurences(article, keyValue);
        if (occ > occurences) {
            occurences = occ;
            index = i;
        }
    }

    return index;
}


int find_occurences(string article, string key) {
    int count = 0;
    if (article.size() < key.size()) {
        return 0;
    }

    for (int i = 0; i < article.size() - key.size() + 1; ++i) {
        for (int j = 0; j < key.size(); ++j) {
            char a = key.at(j);
            char b = article.at(i + j);
            if (key.at(j) != article.at(i + j)) {
                break;
            } else if (key.size() - 1 == j) {
                count++;
            }
        }
    }

    return count;
}


}

