package com.yondervision.mi.util.security;

import java.lang.Math;
import java.io.*;

public class DES5 {
  private static final boolean ED0 = false; /* MODE == encrypt */
  private static final boolean ED1 = true; /* MODE == decrypt */

  private static int[] KnL = new int[32];
  private static int[] KnR = new int[32];
  private static int[] Kn3 = new int[32];

  private static byte[] Df_Key = {
      0x01, 0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xab, (byte) 0xcd,
      (byte) 0xef,
      (byte) 0xfe, (byte) 0xdc, (byte) 0xba, (byte) 0x98, 0x76, 0x54, 0x32,
      0x10,
      (byte) 0x89, (byte) 0xab, (byte) 0xcd, (byte) 0xef, 0x01, 0x23, 0x45,
      0x67
  };

  /**
     private static byte[] bytebit = {
      (byte) 0x80, (byte) 0x40, (byte) 0x20, (byte) 0x10,
      (byte) 0x08, (byte) 0x04, (byte) 0x02, (byte) 0x01
     };
   **/
  private static short[] bytebit = {
      0200, 0100, 040, 020, 010, 04, 02, 01
  };

  private static int[] bigbyte = {
      0x800000, 0x400000, 0x200000, 0x100000,
      0x080000, 0x040000, 0x020000, 0x010000,
      0x008000, 0x004000, 0x002000, 0x001000,
      0x000800, 0x000400, 0x000200, 0x000100,
      0x000080, 0x000040, 0x000020, 0x000010,
      0x000008, 0x000004, 0x000002, 0x000001
  };
  private static byte[] pc1 = {
      (byte) 56, (byte) 48, (byte) 40, (byte) 32, (byte) 24, (byte) 16,
      (byte) 8,
      (byte) 0, (byte) 57, (byte) 49, (byte) 41, (byte) 33, (byte) 25,
      (byte) 17,
      (byte) 9, (byte) 1, (byte) 58, (byte) 50, (byte) 42, (byte) 34, (byte) 26,
      (byte) 18, (byte) 10, (byte) 2, (byte) 59, (byte) 51, (byte) 43,
      (byte) 35,
      (byte) 62, (byte) 54, (byte) 46, (byte) 38, (byte) 30, (byte) 22,
      (byte) 14,
      (byte) 6, (byte) 61, (byte) 53, (byte) 45, (byte) 37, (byte) 29,
      (byte) 21,
      (byte) 13, (byte) 5, (byte) 60, (byte) 52, (byte) 44, (byte) 36,
      (byte) 28,
      (byte) 20, (byte) 12, (byte) 4, (byte) 27, (byte) 19, (byte) 11, (byte) 3
  };
  private static int[] totrot = {
      1, 2, 4, 6, 8, 10, 12, 14, 15, 17, 19, 21, 23, 25, 27, 28
  };

  private static byte[] pc2 = {
      (byte) 13, (byte) 16, (byte) 10, (byte) 23, (byte) 0, (byte) 4,
      (byte) 2, (byte) 27, (byte) 14, (byte) 5, (byte) 20, (byte) 9,
      (byte) 22, (byte) 18, (byte) 11, (byte) 3, (byte) 25, (byte) 7,
      (byte) 15, (byte) 6, (byte) 26, (byte) 19, (byte) 12, (byte) 1,
      (byte) 40, (byte) 51, (byte) 30, (byte) 36, (byte) 46, (byte) 54,
      (byte) 29, (byte) 39, (byte) 50, (byte) 44, (byte) 32, (byte) 47,
      (byte) 43, (byte) 48, (byte) 38, (byte) 55, (byte) 33, (byte) 52,
      (byte) 45, (byte) 41, (byte) 49, (byte) 35, (byte) 28, (byte) 31,
  };

  private static int[] SP1 = {
      0x01010400, 0x00000000, 0x00010000, 0x01010404,
      0x01010004, 0x00010404, 0x00000004, 0x00010000,
      0x00000400, 0x01010400, 0x01010404, 0x00000400,
      0x01000404, 0x01010004, 0x01000000, 0x00000004,
      0x00000404, 0x01000400, 0x01000400, 0x00010400,
      0x00010400, 0x01010000, 0x01010000, 0x01000404,
      0x00010004, 0x01000004, 0x01000004, 0x00010004,
      0x00000000, 0x00000404, 0x00010404, 0x01000000,
      0x00010000, 0x01010404, 0x00000004, 0x01010000,
      0x01010400, 0x01000000, 0x01000000, 0x00000400,
      0x01010004, 0x00010000, 0x00010400, 0x01000004,
      0x00000400, 0x00000004, 0x01000404, 0x00010404,
      0x01010404, 0x00010004, 0x01010000, 0x01000404,
      0x01000004, 0x00000404, 0x00010404, 0x01010400,
      0x00000404, 0x01000400, 0x01000400, 0x00000000,
      0x00010004, 0x00010400, 0x00000000, 0x01010004
  };
  private static int[] SP2 = {
      0x80108020, 0x80008000, 0x00008000, 0x00108020,
      0x00100000, 0x00000020, 0x80100020, 0x80008020,
      0x80000020, 0x80108020, 0x80108000, 0x80000000,
      0x80008000, 0x00100000, 0x00000020, 0x80100020,
      0x00108000, 0x00100020, 0x80008020, 0x00000000,
      0x80000000, 0x00008000, 0x00108020, 0x80100000,
      0x00100020, 0x80000020, 0x00000000, 0x00108000,
      0x00008020, 0x80108000, 0x80100000, 0x00008020,
      0x00000000, 0x00108020, 0x80100020, 0x00100000,
      0x80008020, 0x80100000, 0x80108000, 0x00008000,
      0x80100000, 0x80008000, 0x00000020, 0x80108020,
      0x00108020, 0x00000020, 0x00008000, 0x80000000,
      0x00008020, 0x80108000, 0x00100000, 0x80000020,
      0x00100020, 0x80008020, 0x80000020, 0x00100020,
      0x00108000, 0x00000000, 0x80008000, 0x00008020,
      0x80000000, 0x80100020, 0x80108020, 0x00108000
  };
  private static int[] SP3 = {
      0x00000208, 0x08020200, 0x00000000, 0x08020008,
      0x08000200, 0x00000000, 0x00020208, 0x08000200,
      0x00020008, 0x08000008, 0x08000008, 0x00020000,
      0x08020208, 0x00020008, 0x08020000, 0x00000208,
      0x08000000, 0x00000008, 0x08020200, 0x00000200,
      0x00020200, 0x08020000, 0x08020008, 0x00020208,
      0x08000208, 0x00020200, 0x00020000, 0x08000208,
      0x00000008, 0x08020208, 0x00000200, 0x08000000,
      0x08020200, 0x08000000, 0x00020008, 0x00000208,
      0x00020000, 0x08020200, 0x08000200, 0x00000000,
      0x00000200, 0x00020008, 0x08020208, 0x08000200,
      0x08000008, 0x00000200, 0x00000000, 0x08020008,
      0x08000208, 0x00020000, 0x08000000, 0x08020208,
      0x00000008, 0x00020208, 0x00020200, 0x08000008,
      0x08020000, 0x08000208, 0x00000208, 0x08020000,
      0x00020208, 0x00000008, 0x08020008, 0x00020200
  };
  private static int[] SP4 = {
      0x00802001, 0x00002081, 0x00002081, 0x00000080,
      0x00802080, 0x00800081, 0x00800001, 0x00002001,
      0x00000000, 0x00802000, 0x00802000, 0x00802081,
      0x00000081, 0x00000000, 0x00800080, 0x00800001,
      0x00000001, 0x00002000, 0x00800000, 0x00802001,
      0x00000080, 0x00800000, 0x00002001, 0x00002080,
      0x00800081, 0x00000001, 0x00002080, 0x00800080,
      0x00002000, 0x00802080, 0x00802081, 0x00000081,
      0x00800080, 0x00800001, 0x00802000, 0x00802081,
      0x00000081, 0x00000000, 0x00000000, 0x00802000,
      0x00002080, 0x00800080, 0x00800081, 0x00000001,
      0x00802001, 0x00002081, 0x00002081, 0x00000080,
      0x00802081, 0x00000081, 0x00000001, 0x00002000,
      0x00800001, 0x00002001, 0x00802080, 0x00800081,
      0x00002001, 0x00002080, 0x00800000, 0x00802001,
      0x00000080, 0x00800000, 0x00002000, 0x00802080
  };
  private static int[] SP5 = {
      0x00000100, 0x02080100, 0x02080000, 0x42000100,
      0x00080000, 0x00000100, 0x40000000, 0x02080000,
      0x40080100, 0x00080000, 0x02000100, 0x40080100,
      0x42000100, 0x42080000, 0x00080100, 0x40000000,
      0x02000000, 0x40080000, 0x40080000, 0x00000000,
      0x40000100, 0x42080100, 0x42080100, 0x02000100,
      0x42080000, 0x40000100, 0x00000000, 0x42000000,
      0x02080100, 0x02000000, 0x42000000, 0x00080100,
      0x00080000, 0x42000100, 0x00000100, 0x02000000,
      0x40000000, 0x02080000, 0x42000100, 0x40080100,
      0x02000100, 0x40000000, 0x42080000, 0x02080100,
      0x40080100, 0x00000100, 0x02000000, 0x42080000,
      0x42080100, 0x00080100, 0x42000000, 0x42080100,
      0x02080000, 0x00000000, 0x40080000, 0x42000000,
      0x00080100, 0x02000100, 0x40000100, 0x00080000,
      0x00000000, 0x40080000, 0x02080100, 0x40000100
  };
  private static int[] SP6 = {
      0x20000010, 0x20400000, 0x00004000, 0x20404010,
      0x20400000, 0x00000010, 0x20404010, 0x00400000,
      0x20004000, 0x00404010, 0x00400000, 0x20000010,
      0x00400010, 0x20004000, 0x20000000, 0x00004010,
      0x00000000, 0x00400010, 0x20004010, 0x00004000,
      0x00404000, 0x20004010, 0x00000010, 0x20400010,
      0x20400010, 0x00000000, 0x00404010, 0x20404000,
      0x00004010, 0x00404000, 0x20404000, 0x20000000,
      0x20004000, 0x00000010, 0x20400010, 0x00404000,
      0x20404010, 0x00400000, 0x00004010, 0x20000010,
      0x00400000, 0x20004000, 0x20000000, 0x00004010,
      0x20000010, 0x20404010, 0x00404000, 0x20400000,
      0x00404010, 0x20404000, 0x00000000, 0x20400010,
      0x00000010, 0x00004000, 0x20400000, 0x00404010,
      0x00004000, 0x00400010, 0x20004010, 0x00000000,
      0x20404000, 0x20000000, 0x00400010, 0x20004010
  };
  private static int[] SP7 = {
      0x00200000, 0x04200002, 0x04000802, 0x00000000,
      0x00000800, 0x04000802, 0x00200802, 0x04200800,
      0x04200802, 0x00200000, 0x00000000, 0x04000002,
      0x00000002, 0x04000000, 0x04200002, 0x00000802,
      0x04000800, 0x00200802, 0x00200002, 0x04000800,
      0x04000002, 0x04200000, 0x04200800, 0x00200002,
      0x04200000, 0x00000800, 0x00000802, 0x04200802,
      0x00200800, 0x00000002, 0x04000000, 0x00200800,
      0x04000000, 0x00200800, 0x00200000, 0x04000802,
      0x04000802, 0x04200002, 0x04200002, 0x00000002,
      0x00200002, 0x04000000, 0x04000800, 0x00200000,
      0x04200800, 0x00000802, 0x00200802, 0x04200800,
      0x00000802, 0x04000002, 0x04200802, 0x04200000,
      0x00200800, 0x00000000, 0x00000002, 0x04200802,
      0x00000000, 0x00200802, 0x04200000, 0x00000800,
      0x04000002, 0x04000800, 0x00000800, 0x00200002
  };
  private static int[] SP8 = {
      0x10001040, 0x00001000, 0x00040000, 0x10041040,
      0x10000000, 0x10001040, 0x00000040, 0x10000000,
      0x00040040, 0x10040000, 0x10041040, 0x00041000,
      0x10041000, 0x00041040, 0x00001000, 0x00000040,
      0x10040000, 0x10000040, 0x10001000, 0x00001040,
      0x00041000, 0x00040040, 0x10040040, 0x10041000,
      0x00001040, 0x00000000, 0x00000000, 0x10040040,
      0x10000040, 0x10001000, 0x00041040, 0x00040000,
      0x00041040, 0x00040000, 0x10041000, 0x00001000,
      0x00000040, 0x10040040, 0x00001000, 0x00041040,
      0x10001000, 0x00000040, 0x10000040, 0x10040000,
      0x10040040, 0x10000000, 0x00040000, 0x10001040,
      0x00000000, 0x10041040, 0x00040040, 0x10000040,
      0x10040000, 0x10001000, 0x10001040, 0x00000000,
      0x10041040, 0x00041000, 0x00041000, 0x00001040,
      0x00001040, 0x00040040, 0x10000000, 0x10041000
  };

  // Turn an 8-byte key into internal keys.
  private void deskey(byte[] keyBlock, boolean encrypting, int[] KnL) throws
      Exception {
    int i, j, l, m, n;
    int[] pc1m = new int[56];
    int[] pcr = new int[56];
    int[] kn = new int[32];

    for (j = 0; j < 56; ++j) {
      l = pc1[j];
      m = l & 07;
      //System.out.println("l>>>3======="+(l >>> 3));
      pc1m[j] = ( (keyBlock[l >>> 3] & bytebit[m]) != 0) ? 1 : 0;
    }

    for (i = 0; i < 16; ++i) {
      if (encrypting == ED1) {

        m = (15 - i) << 1;
      }
      else {
        m = i << 1;
      }
      n = m + 1;
      kn[m] = kn[n] = 0;
      for (j = 0; j < 28; ++j) {
        l = j + totrot[i];
        if (l < 28) {
          pcr[j] = pc1m[l];
        }
        else {
          pcr[j] = pc1m[l - 28];
        }
      }
      for (j = 28; j < 56; ++j) {
        l = j + totrot[i];
        if (l < 56) {
          pcr[j] = pc1m[l];
        }
        else {
          pcr[j] = pc1m[l - 28];
        }
      }
      for (j = 0; j < 24; ++j) {
        if (pcr[pc2[j]] != 0) {
          kn[m] |= bigbyte[j];
        }
        if (pcr[pc2[j + 24]] != 0) {
          kn[n] |= bigbyte[j];
        }
      }
    }
    cookey(kn, KnL);
  }

  /*
     private void cookey(int[] raw) {
    int[] cook;
    int[] dough = new int[32];
    int i;
    int raw0, raw1;
    int rawi, KnLi;

    cook = dough;
    for (i = 0, rawi = 0, KnLi = 0; i < 16; i++) {
      raw0 = raw[rawi++];
      raw1 = raw[rawi++];
      cook[KnLi] = (raw0 & 0x00fc0000) << 6;
      cook[KnLi] |= (raw0 & 0x00000fc0) << 10;
      cook[KnLi] |= (raw1 & 0x00fc0000) >>> 10;
      cook[KnLi] |= (raw1 & 0x00000fc0) >>> 6;
      ++KnLi;
      cook[KnLi] = (raw0 & 0x0003f000) << 12;
      cook[KnLi] |= (raw0 & 0x0000003f) << 16;
      cook[KnLi] |= (raw1 & 0x0003f000) >>> 4;
      cook[KnLi] |= (raw1 & 0x0000003f);
      ++KnLi;
    }
    usekey(dough);
    return;
     }
   */

  private void cookey(int[] raw, int KnL[]) throws Exception {
    int raw0, raw1;
    int rawi, KnLi;
    int i;

    for (i = 0, rawi = 0, KnLi = 0; i < 16; ++i) {
      raw0 = raw[rawi++];
      raw1 = raw[rawi++];
      KnL[KnLi] = (raw0 & 0x00fc0000) << 6;
      KnL[KnLi] |= (raw0 & 0x00000fc0) << 10;
      KnL[KnLi] |= (raw1 & 0x00fc0000) >>> 10;
      KnL[KnLi] |= (raw1 & 0x00000fc0) >>> 6;
      ++KnLi;
      KnL[KnLi] = (raw0 & 0x0003f000) << 12;
      KnL[KnLi] |= (raw0 & 0x0000003f) << 16;
      KnL[KnLi] |= (raw1 & 0x0003f000) >>> 4;
      KnL[KnLi] |= (raw1 & 0x0000003f);
      ++KnLi;
    }
  }

  private void scrunch(byte[] raw, int[] raw2) throws Exception {
    int rawi, KnLi;

    KnLi = 0;
    rawi = 0;
    raw2[KnLi] = (raw[rawi++] & 0xff) << 24;
    raw2[KnLi] |= (raw[rawi++] & 0xff) << 16;
    raw2[KnLi] |= (raw[rawi++] & 0xff) << 8;
    raw2[KnLi] |= (raw[rawi++] & 0xff);
    ++KnLi;
    raw2[KnLi] = (raw[rawi++] & 0xff) << 24;
    raw2[KnLi] |= (raw[rawi++] & 0xff) << 16;
    raw2[KnLi] |= (raw[rawi++] & 0xff) << 8;
    raw2[KnLi] |= (raw[rawi] & 0xff);
    return;
  }

  private void unscrun(int[] raw, byte[] raw2) throws Exception {
    int rawi, KnLi;

    KnLi = 0;
    rawi = 0;
    raw2[KnLi++] = (byte) ( (raw[rawi] >> 24) & 0xff);
    raw2[KnLi++] = (byte) ( (raw[rawi] >> 16) & 0xff);
    raw2[KnLi++] = (byte) ( (raw[rawi] >> 8) & 0xff);
    raw2[KnLi++] = (byte) (raw[rawi] & 0xff);
    ++rawi;
    raw2[KnLi++] = (byte) ( (raw[rawi] >> 24) & 0xff);
    raw2[KnLi++] = (byte) ( (raw[rawi] >> 16) & 0xff);
    raw2[KnLi++] = (byte) ( (raw[rawi] >> 8) & 0xff);
    raw2[KnLi] = (byte) (raw[rawi] & 0xff);

    return;
  }

  // The DES function.
  private void desfunc(int[] inInts, int[] keys) throws Exception {
    int fval, work, right, leftt;
    int round;
    int keysi = 0;

    leftt = inInts[0];
    right = inInts[1];

    work = ( (leftt >>> 4) ^ right) & 0x0f0f0f0f;
    right ^= work;
    leftt ^= (work << 4);

    work = ( (leftt >>> 16) ^ right) & 0x0000ffff;
    right ^= work;
    leftt ^= (work << 16);

    work = ( (right >>> 2) ^ leftt) & 0x33333333;
    leftt ^= work;
    right ^= (work << 2);

    work = ( (right >>> 8) ^ leftt) & 0x00ff00ff;
    leftt ^= work;
    right ^= (work << 8);
    right = ( (right << 1) | ( (right >>> 31) & 1)) & 0xffffffff;

    work = (leftt ^ right) & 0xaaaaaaaa;
    leftt ^= work;
    right ^= work;
    leftt = ( (leftt << 1) | ( (leftt >>> 31) & 1)) & 0xffffffff;

    for (round = 0; round < 8; round++) {
      work = (right << 28) | (right >>> 4);
      work ^= keys[keysi++];
      fval = SP7[work & 0x0000003f];
      fval |= SP5[ (work >>> 8) & 0x0000003f];
      fval |= SP3[ (work >>> 16) & 0x0000003f];
      fval |= SP1[ (work >>> 24) & 0x0000003f];
      work = right ^ keys[keysi++];
      fval |= SP8[work & 0x0000003f];
      fval |= SP6[ (work >>> 8) & 0x0000003f];
      fval |= SP4[ (work >>> 16) & 0x0000003f];
      fval |= SP2[ (work >>> 24) & 0x0000003f];
      leftt ^= fval;
      work = (leftt << 28) | (leftt >>> 4);
      work ^= keys[keysi++];
      fval = SP7[work & 0x0000003f];
      fval |= SP5[ (work >>> 8) & 0x0000003f];
      fval |= SP3[ (work >>> 16) & 0x0000003f];
      fval |= SP1[ (work >>> 24) & 0x0000003f];
      work = leftt ^ keys[keysi++];
      fval |= SP8[work & 0x0000003f];
      fval |= SP6[ (work >>> 8) & 0x0000003f];
      fval |= SP4[ (work >>> 16) & 0x0000003f];
      fval |= SP2[ (work >>> 24) & 0x0000003f];
      right ^= fval;
    }

    right = (right << 31) | (right >>> 1);
    work = (leftt ^ right) & 0xaaaaaaaa;
    leftt ^= work;
    right ^= work;
    leftt = (leftt << 31) | (leftt >>> 1);
    work = ( (leftt >>> 8) ^ right) & 0x00ff00ff;
    right ^= work;
    leftt ^= (work << 8);
    work = ( (leftt >>> 2) ^ right) & 0x33333333;
    right ^= work;
    leftt ^= (work << 2);
    work = ( (right >>> 16) ^ leftt) & 0x0000ffff;
    leftt ^= work;
    right ^= (work << 16);
    work = ( (right >>> 4) ^ leftt) & 0x0f0f0f0f;
    leftt ^= work;
    right ^= (work << 4);
    inInts[0] = right;
    inInts[1] = leftt;
  }

  private void des2key(byte[] hexkey, boolean encrypting) throws Exception {
    boolean revmod;
    byte[] hexkey8 = new byte[8];
    int i;

    revmod = (encrypting == ED0) ? ED1 : ED0;
    for (i = 0; i < 8; i++) {
      hexkey8[i] = hexkey[8 + i];
    }
    deskey(hexkey8, revmod, KnL);
    cpkey(KnR);
    deskey(hexkey, encrypting, KnL);
    cpkey(Kn3); /* Kn3 = KnL */
    return;
  }

  private void cpkey(int[] into) throws Exception {
    int KnLi;
    int KnLi1;

    KnLi = 0;
    KnLi1 = 0;

    //System.out.println(KnL[0]);
    while (KnLi < 32) {
      into[KnLi++] = KnL[KnLi1++];
    }
    return;
  }

  private void usekey(int[] from) throws Exception {
    int[] to;
    int KnLi;
    int KnLi1;

    KnLi = 0;
    KnLi1 = 0;

    to = KnL;
    while (KnLi < 32) {
      to[KnLi++] = from[KnLi1++];
    }
    return;
  }

  private void cp3key(int[] into) throws Exception {
    int KnLi;
    int KnLi1;
    KnLi = 0;
    KnLi1 = 0;
    cpkey(into);

    while (KnLi < 32) {
      into[32 + (KnLi++)] = KnR[KnLi1++];
    }

    KnLi = 0;
    KnLi1 = 0;
    while (KnLi < 32) {
      into[64 + (KnLi++)] = Kn3[KnLi1++];
    }
    return;
  }

  private void use3key(int[] from) throws Exception {
    int KnLi;
    int KnLi1;
    KnLi = 0;
    KnLi1 = 0;
    usekey(from);

    while (KnLi < 32) {
      KnR[KnLi++] = from[32 + (KnLi1++)];
    }

    KnLi = 0;
    KnLi1 = 0;
    while (KnLi < 32) {
      Kn3[KnLi++] = from[64 + (KnLi1++)];
    }
    return;
  }

  private void Ddes(byte[] from, byte[] into) throws Exception {
    int[] work = new int[2];

    scrunch(from, work);
    //System.out.println("work[0]=====" + work[0]);
    //System.out.println("work[1]=====" + work[1]);
    desfunc(work, KnL);
    desfunc(work, KnR);
    desfunc(work, Kn3);
    unscrun(work, into);
  }

  private void make2key(char[] aptr, byte[] kptr) throws Exception {
    int i;
    boolean first = true;
    int[] savek = new int[96];
    cp3key(savek);
    des2key(Df_Key, ED0);
    //System.out.println("KnL====+" + KnL[0]);
    //System.out.println("KnR====+" + KnR[0]);
    //System.out.println("Kn3====+" + Kn3[0]);
    for (i = 0; i < 16; i++) {
      kptr[i] = Df_Key[i];
    }

    //while (aptr[0] != '\0' || first) {
    while (first) {
      //store = kptr;
      //for (i = 0; i < 16 && (aptr[i] != 0); i++) {
      for (i = 0; i < 16; i++) {
        kptr[i] ^= aptr[i] & 0x7f;
        //aptr[i] = '\0';
      }
      D2des(kptr, kptr);
      //String ent = byteArr2HexStr(kptr);
      //System.out.println("���ܺ���ַ�:" + ent);
      first = false;
    }
    use3key(savek);
    return;
  }

  private void D2des(byte[] from, byte[] into) throws Exception {
    int[] right;
    int swap, l1, i;
    int[] leftt = new int[2];
    int[] bufR = new int[2];
    byte[] from8 = new byte[8];
    byte[] into8 = new byte[8];

    right = bufR;
    l1 = leftt[1];
    scrunch(from, leftt);

    for (i = 0; i < 8; i++) {
      from8[i] = from[8 + i];
    }
    scrunch(from8, right);
    desfunc(leftt, KnL);
    desfunc(right, KnL);

    swap = leftt[1];
    leftt[1] = right[0];
    right[0] = swap;

    desfunc(leftt, KnR);
    desfunc(right, KnR);

    swap = leftt[1];
    leftt[1] = right[0];
    right[0] = swap;

    desfunc(leftt, Kn3);
    desfunc(right, Kn3);

    unscrun(leftt, into);

    for (i = 0; i < 8; i++) {
      into8[i] = into[8 + i];
    }
    unscrun(right, into8);
    for (i = 0; i < 8; i++) {
      into[i + 8] = into8[i];
    }

    return;
  }

  public static String byteArr2HexStr(byte[] arrB) throws Exception {
    int iLen = arrB.length;
    //ÿ��byte��}���ַ���ܱ�ʾ�������ַ�ĳ��������鳤�ȵ�}��
    StringBuffer sb = new StringBuffer(iLen * 2);
    for (int i = 0; i < iLen; i++) {
      int intTmp = arrB[i];

      //�Ѹ���ת��Ϊ����
      while (intTmp < 0) {
        intTmp = intTmp + 256;
      }
      //System.out.println("intTmp==" + intTmp);
      //С��0F������Ҫ��ǰ�油0
      if (intTmp < 16) {
        sb.append("0");
      }
      sb.append(Integer.toString(intTmp, 16));
    }
    return sb.toString();
  }

  /*
   * ��  ��:  ���ܿͻ�����
   *
   * ��  ��:
   *          1. acPass       4-12   ��������      IN     ������ '0'-->'9'
   *          2. acAcct              �ʺ�          IN     �����ǿ�,������Ϊ��
   *          3. PIN_block    16     �������Ĵ�    OUT
   *
   * ����ֵ: String
   *
   */
  private String PIN_Encrypt(String accPass, String acAcct) throws Exception {

    int len = 0;
    int lenAcct = 0;
    int i = 0;
    int off = 0;
    StringBuffer PIN = new StringBuffer();
    StringBuffer PAN = new StringBuffer();
    StringBuffer acTmp = new StringBuffer();
    String mmLen = null;

    byte[] PAN_hex = new byte[8];
    byte[] PIN_hex = new byte[8];
    byte[] PIN_block_hex = new byte[8];
    byte[] key = new byte[16];

    /* PINԤ���� */
    len = accPass.length();

    lenAcct = acAcct.length();
    PAN.append("0000000000000000");
    PIN.append("FFFFFFFFFFFFFFFF");
    PIN.replace(0, 1, "0");

    if (len >= 10) {
      mmLen = Int2String(len);
    }
    PIN.replace(1, 2, (len < 10) ? Integer.toString(len) : mmLen);
    System.out.println("PIN1=" + PIN);
    PIN.replace(2, len + 2, accPass);

    System.out.println("PIN2=" + PIN);

    /* PAN Ԥ���� */
    if (acAcct.equals("") || acAcct == null) {
      acTmp.append("000000000000"); /* ����ʱ: 12λ0 */
    }
    else if (lenAcct < 12) {
      acTmp.append("000000000000");
      acTmp.replace(0, lenAcct, acAcct);
    }
    else if (lenAcct  == 12 ) {
      acTmp.append("000000000000");
      acTmp.replace(1, lenAcct, acAcct.substring(0,11));
    }
    else {
      off = acAcct.length() - 12 - 1;
      acTmp.replace(off, 12 + off, acAcct);
    }

    PAN.replace(4, 16, acTmp.toString());
    System.out.println("PIN===" + PIN.toString());
    System.out.println("PAN===" + PAN.toString());

    //strtohex(PIN.toString(), PIN_hex, 8);
    //strtohex(PAN.toString(), PAN_hex, 8);
    PIN_hex = HexString2Bytes(PIN.toString());
    PAN_hex = HexString2Bytes(PAN.toString());

    /*
         String ent_PAN = byteArr2HexStr(PAN_hex);
         String ent_PIN = byteArr2HexStr(PIN_hex);
         System.out.println("ԭʼ�ַ�PIN:" + ent_PIN);
         System.out.println("ԭʼ�ַ�PAN:" + ent_PAN);
     */

    for (i = 0; i < 8; i++) {
      PIN_block_hex[i] = PAN_hex[i] ^= PIN_hex[i];
    }

    make2key(PAN.toString().toCharArray(), key);
    //String ent1 = byteArr2HexStr(key);
    //System.out.println("���ܺ���ַ�:" + ent1);

    des2key(key, ED0);

    Ddes(PIN_block_hex, PIN_block_hex);

    String ent = byteArr2HexStr(PIN_block_hex);
    //System.out.println("���ܺ���ַ�:" + ent);

    return ent;
  }

  private int String2Int(String src) throws Exception {
    int len = 0;
    if (src.equals("1")) {
      len = 1;
    }
    if (src.equals("2")) {
      len = 2;
    }
    if (src.equals("3")) {
      len = 3;
    }
    if (src.equals("4")) {
      len = 4;
    }
    if (src.equals("5")) {
      len = 5;
    }
    if (src.equals("6")) {
      len = 6;
    }
    if (src.equals("7")) {
      len = 7;
    }
    if (src.equals("8")) {
      len = 8;
    }
    if (src.equals("9")) {
      len = 9;
    }
    if (src.equals("A")) {
      len = 10;
    }
    if (src.equals("B")) {
      len = 11;
    }
    if (src.equals("C")) {
      len = 12;
    }
    if (src.equals("D")) {
      len = 13;
    }
    if (src.equals("E")) {
      len = 14;
    }
    if (src.equals("F")) {
      len = 15;
    }
    return len;
  }

  private String Int2String(int len) throws Exception {
    String mmLen = null;

    switch (len) {
      case 10:
        mmLen = "A";
        break;
      case 11:
        mmLen = "B";
        break;
      case 12:
        mmLen = "C";
        break;
      case 13:
        mmLen = "D";
        break;
      case 14:
        mmLen = "E";
        break;
      case 15:
        mmLen = "F";
        break;
    }

    return mmLen;
  }

  /**
   *
   * @param b byte[]
   * @return String
   */
  public static String Bytes2HexString(byte[] b) throws Exception {
    String ret = "";
    for (int i = 0; i < b.length; i++) {
      String hex = Integer.toHexString(b[i] & 0xFF);
      if (hex.length() == 1) {
        hex = '0' + hex;
      }
      ret += hex.toUpperCase();
    }
    return ret;
  }

  /**
   * ��}��ASCII�ַ�ϳ�һ���ֽڣ�
   * �磺"EF"--> 0xEF
   * @param src0 byte
   * @param src1 byte
   * @return byte
   */
  private byte uniteBytes(byte src0, byte src1) throws Exception {
    byte _b0 = Byte.decode("0x" + new String(new byte[] {src0})).byteValue();
    _b0 = (byte) (_b0 << 4);
    byte _b1 = Byte.decode("0x" + new String(new byte[] {src1})).byteValue();
    byte ret = (byte) (_b0 ^ _b1);
    return ret;
  }

  /**
   * ��ָ���ַ�src����ÿ}���ַ�ָ�ת��Ϊ16������ʽ
   * �磺"2B44EFD9" --> byte[]{0x2B, 0x44, 0xEF, 0xD9}
   * @param src String
   * @return byte[]
   */
  private byte[] HexString2Bytes(String src) throws Exception {
    byte[] ret = new byte[8];
    byte[] tmp = src.getBytes();
    for (int i = 0; i < 8; i++) {
      ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
    }
    return ret;
  }

  public static void main(String[] args) {
    String MM = null;
    String MMMM = null;
    String acPass = null;
    String fString = null;
    //StringBuffer acPass_Init = new StringBuffer() ;
    StringBuffer MM_PIN = new StringBuffer();
    DES5 des5 = new DES5();
    char PAN[] = new char[16];
    byte[] key = new byte[16];
    int i;
    long j;
    byte PIN_block_hex[] = new byte[8];

    try {
      /**
             for (j = 0; j < 16; j++) {
        PAN[j] = '0';
             }
             for (i = 0; i < 8; i++) {
        PIN_block_hex[i] = 1;
             }
       */

      /*
      PrintWriter out1 = new PrintWriter(
          new BufferedWriter(new FileWriter("c:/test_java.txt")));

      for (i = 6; i <= 6; i++) {
        StringBuffer acPass_Init = new StringBuffer();
        acPass_Init.append("000000000000");
        //System.out.println("�������I:" + i);
        for (j = 0; j <= (Math.pow(10, i) - 1); j++) {
          //System.out.println("�������J==:" + Long.toString(j).length());
          acPass_Init.replace(12 - (Long.toString(j).length()), 12,
                              Long.toString(j));
          acPass = acPass_Init.substring(12 - i, 12);
          //System.out.println("�������acPass:" + acPass);
          MM = des5.PIN_Encrypt(acPass, "");
          MM_PIN.append(MM + "\n");
          //System.out.println("�������MM:" + MM);
        }
      }
      BufferedReader in4 = new BufferedReader(new StringReader(MM_PIN.toString()));

      int lineCount = 1;
      while ( (fString = in4.readLine()) != null) {
        out1.println(fString);
      }
      out1.close();
     */
      MM = des5.PIN_Encrypt("111111", "00000000000");
      System.out.println("���ܺ���ַ�MM:" + MM);
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
